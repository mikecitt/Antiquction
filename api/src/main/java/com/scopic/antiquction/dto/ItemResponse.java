package com.scopic.antiquction.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ItemResponse {
    private Long id;

    private String name;

    private String description;

    private Integer startPrice;

    private Date dateEnd;

    private List<BidResponse> bids;
}
