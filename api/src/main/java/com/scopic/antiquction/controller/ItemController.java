package com.scopic.antiquction.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.scopic.antiquction.dto.BillResponse;
import com.scopic.antiquction.dto.ItemRequest;
import com.scopic.antiquction.dto.ItemResponse;
import com.scopic.antiquction.model.AutoBid;
import com.scopic.antiquction.model.Bid;
import com.scopic.antiquction.model.Item;
import com.scopic.antiquction.model.User;
import com.scopic.antiquction.service.ItemService;
import com.scopic.antiquction.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR')")
    public ResponseEntity<Page<ItemResponse>> getItems(
        @RequestParam(defaultValue = "") String text,
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String direction
    ) {
        Page<Item> items = itemService.findAll(text, pageNo, pageSize, sortBy, direction);
        Page<ItemResponse> itemsDTO = items.map(item -> modelMapper.map(item, ItemResponse.class));
        for(ItemResponse itemResponse : itemsDTO.getContent()) {
            if(itemResponse.getBids().size() > 0)
                itemResponse.setPrice(itemResponse.getBids().get(itemResponse.getBids().size() - 1).getBidPrice());
        }

        return new ResponseEntity<>(itemsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGULAR')")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Long id, Principal user) {
        Optional<User> loggedUser = userService.findUser(user.getName());
        if(loggedUser == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Item item = itemService.findOne(id);
        ItemResponse itemResponse = modelMapper.map(item, ItemResponse.class);
        if(itemResponse.getBids().size() > 0)
            itemResponse.setPrice(itemResponse.getBids().get(itemResponse.getBids().size() - 1).getBidPrice());
        
        for(AutoBid a : item.getAutoBids())
            if(a.getUser() == loggedUser.get()) {
                itemResponse.setAutoBid(a.getMaxBidPrice());
            }
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ItemResponse> addItem(@RequestBody ItemRequest itemDTO) {
        Item item = modelMapper.map(itemDTO, Item.class);
        item.setBids(new ArrayList<Bid>());
        Item i = itemService.add(item);
        return new ResponseEntity<>(modelMapper.map(i, ItemResponse.class), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ItemResponse> updateItem(@RequestBody ItemRequest itemDTO, @PathVariable Long id) {
        Item item = modelMapper.map(itemDTO, Item.class);
        Item i = itemService.update(id, item);
        if(i == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(modelMapper.map(i, ItemResponse.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/bid")
    @PreAuthorize("hasRole('ROLE_REGULAR')")
    public ResponseEntity<ItemResponse> bidItem(@PathVariable Long id, @RequestParam Integer bidPrice, Principal user) {
        Optional<User> loggedUser = userService.findUser(user.getName());
        if(loggedUser == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if(itemService.findOne(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Bid bid = new Bid();
        bid.setBidPrice(bidPrice);
        bid.setUser(loggedUser.get());

        if(itemService.findOne(id).getDateEnd().before(new Date()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
        Item biddedItem = itemService.bid(bid, id, loggedUser.get().getId());

        if(biddedItem == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        
        return new ResponseEntity<>(modelMapper.map(biddedItem, ItemResponse.class), HttpStatus.OK);
    }

    @PostMapping("/{id}/autobid")
    @PreAuthorize("hasRole('ROLE_REGULAR')")
    public ResponseEntity<ItemResponse> autobidItem(@PathVariable Long id, @RequestParam Integer maxBidPrice, Principal user) {
        Optional<User> loggedUser = userService.findUser(user.getName());
        if(loggedUser == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if(itemService.findOne(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AutoBid autoBid = new AutoBid();
        autoBid.setMaxBidPrice(maxBidPrice);
        autoBid.setUser(loggedUser.get());

        Item i = this.itemService.addAutoBid(autoBid, id, loggedUser.get().getId());
        
        return new ResponseEntity<>(modelMapper.map(i, ItemResponse.class), HttpStatus.OK);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('ROLE_REGULAR')")
    public ResponseEntity<List<ItemResponse>> getMyBiddingItems(Principal user) {
        Optional<User> loggedUser = userService.findUser(user.getName());
        if(loggedUser == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        List<Item> items = itemService.getUserBiddingItems(loggedUser.get().getId());
        List<ItemResponse> itemResponses = items.stream().map(item -> modelMapper.map(item, ItemResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(itemResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}/bill")
    @PreAuthorize("hasRole('ROLE_REGULAR')")
    public ResponseEntity<BillResponse> getBill(@PathVariable Long id, Principal user) {
        Optional<User> loggedUser = userService.findUser(user.getName());
        if(loggedUser == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if(itemService.findOne(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        BillResponse response = itemService.getBill(id, loggedUser.get().getId());
        if(response == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
