package com.scopic.antiquction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillResponse {
    private String itemName;
    private String userUsername;
    private Integer price;
}
