package com.scopic.antiquction.controller;

import java.util.List;

import com.scopic.antiquction.model.Item;
import com.scopic.antiquction.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping(produces = { "application/json" })
    public List<Item> getItems() {
        return itemService.findAll();
    }

    @PostMapping
    public Item addItem(@RequestBody Item item) {
        return itemService.add(item);
    }
}
