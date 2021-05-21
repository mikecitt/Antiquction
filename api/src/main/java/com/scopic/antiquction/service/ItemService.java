package com.scopic.antiquction.service;

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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Page<Item> findAll(String text, Integer pageNo, Integer pageSize, String sortBy, String direction) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.fromString(direction), sortBy));

        Page<Item> pagedResult = repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text, paging);

        return pagedResult;
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
        i.get().setDateEnd(item.getDateEnd());

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

        Optional<User> u = userRepository.findById(itemId);
        if(!u.isPresent())
            return null;
        User user = u.get();

        //item.getAutoBids().stream()
        //for(AutoBid a : item.getAutoBids()) {
        //    if(a.getUser() == user) {
        //        a.setMaxBidPrice(autoBid.getMaxBidPrice());
        //    }
        //}

        return null;
    }

    public Item checkAutoBid(Item item) {
        boolean bidded = false;

        for(AutoBid a : item.getAutoBids()) {
            Bid highestBid = item.getBids().get(item.getBids().size() - 1);
            if(highestBid.getUser().equals(a.getUser()))
                continue;
            if(a.getMaxBidPrice() > highestBid.getBidPrice()) {
                Integer temp = highestBid.getBidPrice();
                highestBid = new Bid();
                highestBid.setUser(a.getUser());
                highestBid.setBidPrice(temp + 1);
                item.getBids().add(highestBid);
                bidded = true;
            }
        }

        return bidded ? checkAutoBid(item) : item;
    }
}
