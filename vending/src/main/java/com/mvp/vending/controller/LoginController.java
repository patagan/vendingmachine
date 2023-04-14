package com.mvp.vending.controller;

import com.mvp.vending.data.LoginInput;
import com.mvp.vending.data.LoginOutput;
import com.mvp.vending.data.entity.Users;
import com.mvp.vending.service.IUserService;
import com.mvp.vending.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final IUserService iUserService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;
    @PostMapping("/login")
    public ResponseEntity<LoginOutput> login(@RequestBody LoginInput loginInput) {
        try {
            Users usersDetails = iUserService.getUserDetails(loginInput);
            boolean matches = passwordEncoder.matches(loginInput.getPassword(), usersDetails.getPassword());
            if (matches) {
                String token = jwtTokenUtil.generateToken(usersDetails.getUsername(), usersDetails.getRole());
                return ResponseEntity.ok(LoginOutput.builder()
                                .id(usersDetails.getId())
                        .username(usersDetails.getUsername())
                        .deposit(usersDetails.getDeposit())
                        .token(token)
                        .role(usersDetails.getRole())
                        .build());
            }
        } catch (UsernameNotFoundException e) {
            log.info("User was not found");
        }
        return ResponseEntity.status(UNAUTHORIZED).build();
    }


}
