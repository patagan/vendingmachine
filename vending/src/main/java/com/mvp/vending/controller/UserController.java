package com.mvp.vending.controller;

import com.mvp.vending.data.BoughtProduct;
import com.mvp.vending.data.BuyInput;
import com.mvp.vending.data.RegistrationForm;
import com.mvp.vending.data.UserDeposit;
import com.mvp.vending.data.entity.Users;
import com.mvp.vending.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;

    private final PasswordEncoder passwordEncoder;
    @PostMapping("/user")
    public ResponseEntity<Long> register(@RequestBody RegistrationForm registrationForm) {

        Users newUsers = Users.builder()
                .username(registrationForm.getUsername())
                .password(passwordEncoder.encode(registrationForm.getPassword()))
                .deposit(0F)
                .role(registrationForm.getRole())
                .build();
        Long userId = iUserService.addUser(newUsers);
        return ResponseEntity.ok(userId);
    }

    @PutMapping(path = "/deposit")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<Float> addDeposit(@RequestBody UserDeposit userDeposit) {

        Float totalDeposit = iUserService.addDeposit(userDeposit);
        return ResponseEntity.ok(totalDeposit);
    }

    @PutMapping(path = "/reset")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity resetDeposit() {

        iUserService.resetDeposit();
        return ResponseEntity.ok(0F);
    }

    @PostMapping(path = "/buyProduct")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<BoughtProduct> buyProduct(@RequestBody BuyInput buyInput) {
        BoughtProduct buyProduct = iUserService.buyProduct(buyInput);
        return ResponseEntity.ok(buyProduct);
    }

    @GetMapping(path = "/myUser/{id}")
    public ResponseEntity<Users> myUser(@PathVariable("id") Long userId) {
        Users foundUsers = iUserService.getUserById(userId);
        return ResponseEntity.ok(foundUsers);
    }
}
