package com.scopic.antiquction.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.scopic.antiquction.model.AutoBid;
import com.scopic.antiquction.model.Bid;
import com.scopic.antiquction.model.Item;
import com.scopic.antiquction.model.User;
import com.scopic.antiquction.repository.ItemRepository;
import com.scopic.antiquction.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public Page<Item> findAll(String text, Integer pageNo, Integer pageSize, String sortBy, String direction) {
        Pageable paging;
        paging = PageRequest.of(pageNo, pageSize, Sort.by(new Sort.Order(Sort.Direction.fromString(direction), sortBy).ignoreCase()).and(Sort.by("id")));

        Page<Item> pagedResult = repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text, paging);

        return pagedResult;
    }

    public List<Item> getUserBiddingItems(Long userId) {
        User u = userRepository.getOne(userId);
        return repository.findByBidsUserOrderByDateEndDesc(u);
    }

    public Item findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }

    public Item add(Item item) {
        return repository.save(item);
    }

    public Item update(Long id, Item item) {
        Optional<Item> i = repository.findById(id);
        if(!i.isPresent())
            return null;

        i.get().setName(item.getName());
        i.get().setDescription(item.getDescription());

        return repository.save(i.get());
    }

    public void delete(Long id) {
        Optional<Item> item = repository.findById(id);
        if(item.isPresent())
            repository.delete(item.get());
        return;
    }

    public Item bid(Bid bid, Long itemId, Long userId) {
        Optional<Item> i = repository.findById(itemId);
        if(!i.isPresent())
            return null;
        Item item = i.get();
        if(item.getDateEnd().before(new Date()))
            return null;
        List<Bid> bids = item.getBids();

        Integer currPrice;
        if(bids.size() > 0) { // if there are some bids
            Bid highestBid = bids.get(bids.size() - 1);
            if(highestBid.getUser().getId() == userId)
                return null;
            currPrice = highestBid.getBidPrice();
        }
        else { // if this is first bid
            currPrice = item.getStartPrice();
        }

        if(bid.getBidPrice() <= currPrice) // if bid price is not higher than current price
            return null;

        item.getBids().add(bid);
        item = checkAutoBid(item);

        return repository.save(item);
    }

    public Item addAutoBid(AutoBid autoBid, Long itemId, Long userId) {
        Optional<Item> i = repository.findById(itemId);
        if(!i.isPresent())
            return null;
        Item item = i.get();

        Optional<User> u = userRepository.findById(userId);
        if(!u.isPresent())
            return null;
        User user = u.get();

        boolean done = false;

        for(AutoBid a : item.getAutoBids()) {
            if(a.getUser() == user) {
                a.setMaxBidPrice(autoBid.getMaxBidPrice());
                done = true;
            }
        }

        if(!done)
            item.getAutoBids().add(autoBid);
            
        item = checkAutoBid(item);
        return repository.save(item);
    }

    private AutoBid reserveUser(AutoBid a, Item i, Integer price) {
        Integer lastReserved = a.getUser().getReservedAutoBid();
        Integer notificationAutoBid = a.getUser().getNotificationAutoBid();
        Integer maxAutoBid = a.getUser().getMaxAutoBid();
        Integer lastBid = 0;
        for(Bid b : i.getBids()) {
            if(b.getUser() == a.getUser())
                lastBid = b.getBidPrice();
        }
        Integer diff = price - lastBid;

        if(maxAutoBid == null)
            return a;
        if(lastReserved + diff <= maxAutoBid) {
            a.getUser().setReservedAutoBid(lastReserved + diff);
            if(notificationAutoBid != null) {
                if( (float) lastReserved / (float) maxAutoBid < (float) notificationAutoBid / 100 && ((float) lastReserved + (float) diff) / (float) maxAutoBid >= (float) notificationAutoBid / 100) {
                    emailService.sendMessage("mikecitt@gmail.com", "Antiquction AutoBid", "You have reached " + notificationAutoBid + "% of maximum bidding ammount.");
                }
            }
            return a;
        }
        emailService.sendMessage("mikecitt@gmail.com", "Antiquction AutoBid", "Your bidding process is stopped. Reached maximum ammount.");
        return null;
    }

    public Item checkAutoBid(Item item) {
        boolean bidded = false;
        AutoBid b = new AutoBid();

        Bid highestBid;

        for(AutoBid a : item.getAutoBids()) {
            if(item.getBids().size() == 0 && a.getMaxBidPrice() > item.getStartPrice()) {
                highestBid = new Bid();
                highestBid.setBidPrice(item.getStartPrice()+1);
                b = reserveUser(a, item, item.getStartPrice()+1);
                if(b == null)
                    return item;
                highestBid.setUser(b.getUser());
                item.getBids().add(highestBid);
                return item;
            }
            highestBid = item.getBids().get(item.getBids().size() - 1);
            if(highestBid.getUser().equals(a.getUser()))
                continue;
            if(a.getMaxBidPrice() > highestBid.getBidPrice()) {
                b = reserveUser(a, item, highestBid.getBidPrice()+1);
                if(b == null)
                    continue;
                Integer temp = highestBid.getBidPrice();
                highestBid = new Bid();
                highestBid.setUser(b.getUser());
                highestBid.setBidPrice(temp + 1);
                item.getBids().add(highestBid);
                bidded = true;
            }
        }

        return bidded ? checkAutoBid(item) : item;
    }
}
