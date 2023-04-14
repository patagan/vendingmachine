package com.mvp.vending.data;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class LoginInput {
    private String username;
    private String password;

    private String token;

}
