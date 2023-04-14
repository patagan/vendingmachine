package com.mvp.vending.data;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class BuyInput {
    private Long productId;

    private Integer amountPurchased;
}
