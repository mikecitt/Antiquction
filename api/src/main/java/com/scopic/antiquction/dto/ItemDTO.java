package com.scopic.antiquction.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ItemDTO {
    private String name;
    private String description;
    private Integer startPrice;
    private Date dateEnd;
}
