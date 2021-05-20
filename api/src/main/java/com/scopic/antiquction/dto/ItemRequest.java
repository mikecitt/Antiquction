package com.scopic.antiquction.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ItemRequest {
    private String name;
    private String description;
    private Integer startPrice;
    private Date dateEnd;
}
