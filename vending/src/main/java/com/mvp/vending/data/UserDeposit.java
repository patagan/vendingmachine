package com.mvp.vending.data;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class UserDeposit {
    private Integer fiveCents;

    private Integer tenCents;
    private Integer twentyCents;
    private Integer fiftyCents;
    private Integer hundredCents;
}
