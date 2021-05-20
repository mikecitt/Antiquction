package com.scopic.antiquction.service;

import java.util.List;
import java.util.Optional;

import com.scopic.antiquction.model.Bid;
import com.scopic.antiquction.model.Item;
import com.scopic.antiquction.repository.ItemRepository;

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
        Item item = repository.getOne(itemId);
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
        return repository.save(item);
    }
}
