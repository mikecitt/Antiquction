package com.scopic.antiquction.repository;

import com.scopic.antiquction.model.Bid;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
    
}
