package com.scopic.antiquction.repository;

import java.util.List;
import java.util.Optional;

import com.scopic.antiquction.model.Item;
import com.scopic.antiquction.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);
    List<Item> findByBidsUserOrderByDateEndDesc(User user);
    List<Item> findByAwarded(Boolean awarded);
    Optional<Item> findById(Long id);
}
