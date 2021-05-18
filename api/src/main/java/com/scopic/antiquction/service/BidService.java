package com.scopic.antiquction.service;

import com.scopic.antiquction.model.Bid;
import com.scopic.antiquction.repository.BidRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {
    @Autowired
    private BidRepository repository;

    public Bid bid(Bid bid) {
        return repository.save(bid);
    }
}
