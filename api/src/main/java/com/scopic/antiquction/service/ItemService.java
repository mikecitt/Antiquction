package com.scopic.antiquction.service;

import java.util.List;

import com.scopic.antiquction.model.Bid;
import com.scopic.antiquction.model.Item;
import com.scopic.antiquction.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;

    public List<Item> findAll() {
        return repository.findAll();
    }

    public Item findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }

    public Item add(Item item) {
        return repository.save(item);
    }

    public Item update(Long id, Item item) {
        Item i = repository.findById(id).orElseGet(null);
        
        if(i == null) 
            return null;

        i.setName(item.getName());
        i.setDescription(item.getDescription());
        i.setDateEnd(item.getDateEnd());

        return repository.save(i);
    }

    public void delete(Long id) {
        Item item = repository.findById(id).orElseGet(null);
        if(item != null)
            repository.delete(item);
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
