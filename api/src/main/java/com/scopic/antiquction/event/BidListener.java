package com.scopic.antiquction.event;

import javax.persistence.PrePersist;

import com.scopic.antiquction.model.Item;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BidListener {
    @PrePersist
    public void onPrePrist(final Item toSave) {
        System.out.println("test event");
    }
}
