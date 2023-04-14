package com.mvp.vending.data;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
public class BoughtProduct {
    private Float depositLeft;

    private Float moneySpent;

    private List<Integer> changeMoney;

    private String productName;
}
