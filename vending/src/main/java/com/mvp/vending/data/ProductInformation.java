package com.mvp.vending.data;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ProductInformation {

    private Long id;
    private Integer amountAvailable;
    private Float cost;
    private String name;
    private Long sellerId;
}
