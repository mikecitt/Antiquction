package com.scopic.antiquction.controller;

import java.security.Principal;
import java.util.Optional;

import com.scopic.antiquction.model.Bid;
import com.scopic.antiquction.model.Item;
import com.scopic.antiquction.model.User;
import com.scopic.antiquction.service.ItemService;
import com.scopic.antiquction.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{id}/{bidPrice}")
    @PreAuthorize("hasRole('ROLE_REGULAR')")
    public ResponseEntity<Item> bidItem(@PathVariable Long id, @PathVariable Integer bidPrice, Principal user) {
        Optional<User> loggedUser = userService.findUser(user.getName());
        if(loggedUser == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if(itemService.findOne(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Bid bid = new Bid();
        bid.setBidPrice(bidPrice);
        bid.setUser(loggedUser.get());
        
        Item biddedItem = itemService.bid(bid, id, loggedUser.get().getId());

        if(biddedItem == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        
        return new ResponseEntity<>(biddedItem, HttpStatus.OK);
    }
}
