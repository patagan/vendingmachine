package com.mvp.vending.controller;

import com.mvp.vending.data.*;
import com.mvp.vending.data.entity.Product;
import com.mvp.vending.data.entity.Users;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class BaseTest {

    public static final String USER_BUYER = "test";

    public static final String USER_SELLER = "test2";
    public static final String PASSWORD = "pass1";
    public static final String BUYER = "BUYER";
    public static final String SELLER = "SELLER";

    @LocalServerPort
    private int port;
    public static final String LOCALHOST = "http://localhost:";
    protected RestTemplate restTemplate= new RestTemplate();;
    protected Product createProduct(Long sellerId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<ProductInformation> request = new HttpEntity<>(ProductInformation.builder()
                .amountAvailable(4)
                .cost(2.95F)
                .name("water")
                .sellerId(sellerId)
                .build(), headers);

        return restTemplate.postForEntity(LOCALHOST + port + "/addProduct", request, Product.class).getBody();
    }

    protected Long registerUser(String username, String password, String role) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Users> request = new HttpEntity<>(Users.builder().username(username).password(password).role(role).build(), headers);
        return restTemplate.postForEntity(LOCALHOST + port + "/user", request, Long.class).getBody();
    }

    protected ResponseEntity<LoginOutput> getLoginInputResponseEntity(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginInput> request = new HttpEntity<>(LoginInput.builder().username(USER_BUYER).password(PASSWORD).build(), headers);

        ResponseEntity<LoginOutput> responseEntity = restTemplate.postForEntity(LOCALHOST + port + "/login", request, LoginOutput.class);
        return responseEntity;
    }

    protected ResponseEntity<Users> getMyUser(Long id, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Set Authorization header
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<Users> response = restTemplate.exchange(LOCALHOST + port + "/myUser/" + id, HttpMethod.GET, entity, Users.class);
        return response;
    }

    protected ResponseEntity<Float> topUpDeposit(UserDeposit userDeposit, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Set Authorization header
        HttpEntity<UserDeposit> entity = new HttpEntity<>(userDeposit, headers);
        ResponseEntity<Float> response = restTemplate.exchange(LOCALHOST + port + "/deposit", HttpMethod.PUT, entity, Float.class);
        return response;
    }

    protected ResponseEntity<Float> reset(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Set Authorization header
        HttpEntity<UserDeposit> entity = new HttpEntity<>(headers);
        ResponseEntity<Float> response = restTemplate.exchange(LOCALHOST + port + "/reset", HttpMethod.PUT, entity, Float.class);
        return response;
    }

    protected ResponseEntity<BoughtProduct> buyProduct(BuyInput buyInput, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Set Authorization header
        HttpEntity<BuyInput> entity = new HttpEntity<>(buyInput, headers);
        ResponseEntity<BoughtProduct> response = restTemplate.exchange(LOCALHOST + port + "/buyProduct", HttpMethod.POST, entity, BoughtProduct.class);
        return response;
    }
}
