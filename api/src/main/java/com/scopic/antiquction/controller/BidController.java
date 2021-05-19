package com.scopic.antiquction.controller;

import java.security.Principal;
import java.util.Optional;

import com.scopic.antiquction.model.Bid;
import com.scopic.antiquction.model.Item;
import com.scopic.antiquction.model.User;
import com.scopic.antiquction.service.ItemService;
import com.scopic.antiquction.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/bid")
public class BidController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_REGULAR')")
    public Item bidItem(@PathVariable Long id, Principal user) {
        Optional<User> loggedUser = userService.findUser(user.getName());
        Bid bid = new Bid();
        bid.setUser(loggedUser.get());
        return itemService.bid(bid, id);
    }
}
