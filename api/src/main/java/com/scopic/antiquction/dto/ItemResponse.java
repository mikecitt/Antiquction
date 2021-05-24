package com.scopic.antiquction.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ItemResponse {
    private Long id;

    private String name;

    private String description;
    
    private Integer startPrice;

    private Integer price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date dateEnd;

    private List<BidResponse> bids;

    private Integer autoBid;
}
