package com.scopic.antiquction.service;

import java.util.List;

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

    public Item add(Item item) {
        return repository.save(item);
    }
}
