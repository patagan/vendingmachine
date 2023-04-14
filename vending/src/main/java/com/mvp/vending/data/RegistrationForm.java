package com.mvp.vending.data;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RegistrationForm {
    private String username;
    private String password;

    private String role;

}
