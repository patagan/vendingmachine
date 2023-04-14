package com.mvp.vending.controller;

import com.mvp.vending.data.*;
import com.mvp.vending.data.entity.Product;
import com.mvp.vending.data.entity.Users;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest  extends BaseTest {

    private Long productId;

    @LocalServerPort
    private int port;

    @BeforeAll
    void setUp() {
        registerUser(USER_BUYER, PASSWORD, BUYER);
        Long sellerId = registerUser(USER_SELLER, PASSWORD, SELLER);
        LoginOutput loginInputResponseEntity = getLoginInputResponseEntity(USER_SELLER, PASSWORD).getBody();
        Product product = createProduct(sellerId, loginInputResponseEntity.getToken());
        productId = product.getId();
    }

    @Test
    public void failedTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginInput> request = new HttpEntity<>(LoginInput.builder().build(), headers);

        assertThrows(HttpClientErrorException.class,() -> restTemplate.postForEntity(LOCALHOST + port + "/login", request, LoginInput.class));
    }


    @Test
    public void givenUserLoginSuccessfully() {
        ResponseEntity<LoginOutput> responseEntity = getLoginInputResponseEntity(USER_BUYER, PASSWORD);

        assertThat(responseEntity.getStatusCode().value(),is(HttpStatus.OK.value()));
        assertThat(responseEntity.getBody().getUsername(), is(USER_BUYER));
    }

    @Test
    public void givenUserLoginWhenAddDepositToUserThenUserHasMoney() {
        ResponseEntity<LoginOutput> responseEntity = getLoginInputResponseEntity(USER_BUYER, PASSWORD);
        LoginOutput body = responseEntity.getBody();
        ResponseEntity<Users> myUser = getMyUser(body.getId(), body.getToken());
        assertThat(myUser.getBody().getDeposit(), is(0F));

        Float newDeposit = topUpDeposit(UserDeposit.builder().fiftyCents(2).fiveCents(1).twentyCents(1).tenCents(0).hundredCents(3).build(), body.getToken()).getBody();
        assertThat(newDeposit, is(4.25F));

        myUser = getMyUser(body.getId(), body.getToken());
        assertThat(myUser.getBody().getDeposit(), is(4.25F));
        reset(body.getToken());
    }

    @Test
    public void givenUserLoginWhenBuyProductWithoutMoneyThenError() {
        ResponseEntity<LoginOutput> responseEntity = getLoginInputResponseEntity(USER_BUYER, PASSWORD);
        LoginOutput body = responseEntity.getBody();
        ResponseEntity<Users> myUser = getMyUser(body.getId(), body.getToken());
        assertThat(myUser.getBody().getDeposit(), is(0F));

        BuyInput buyInput = BuyInput.builder()
                .productId(productId)
                .amountPurchased(3)
                .build();
        assertThrows(HttpClientErrorException.class, () -> buyProduct(buyInput,body.getToken()));
    }

    @Test
    public void givenUserLoginWhenBuyProductWithMoneyThenSuccess() {
        ResponseEntity<LoginOutput> responseEntity = getLoginInputResponseEntity(USER_BUYER, PASSWORD);
        LoginOutput body = responseEntity.getBody();
        ResponseEntity<Users> myUser = getMyUser(body.getId(), body.getToken());
        assertThat(myUser.getBody().getDeposit(), is(0F));

        Float newDeposit = topUpDeposit(UserDeposit.builder().fiftyCents(2).fiveCents(1).twentyCents(1).tenCents(0).hundredCents(13).build(), body.getToken()).getBody();
        assertThat(newDeposit, is(14.25F));

        BuyInput buyInput = BuyInput.builder()
                .productId(productId)
                .amountPurchased(3)
                .build();
        BoughtProduct boughtProduct = buyProduct(buyInput, body.getToken()).getBody();

        assertThat(boughtProduct.getMoneySpent(), is(3*2.95F));
        assertThat(boughtProduct.getChangeMoney(), is(List.of(100,100,100,100,100,20,20)));
        assertThat(boughtProduct.getProductName(), is("water"));
        assertThat(boughtProduct.getDepositLeft(), is(5.4F));
        reset(body.getToken());
    }

    @Test
    public void givenUserLoginWhenBuyToManyProductsThrowError() {
        ResponseEntity<LoginOutput> responseEntity = getLoginInputResponseEntity(USER_BUYER, PASSWORD);
        LoginOutput body = responseEntity.getBody();
        ResponseEntity<Users> myUser = getMyUser(body.getId(), body.getToken());
        assertThat(myUser.getBody().getDeposit(), is(0F));

        Float newDeposit = topUpDeposit(UserDeposit.builder().fiftyCents(2).fiveCents(1).twentyCents(1).tenCents(0).hundredCents(13).build(), body.getToken()).getBody();
        assertThat(newDeposit, is(14.25F));


        BuyInput buyInput = BuyInput.builder()
                .productId(productId)
                .amountPurchased(5)
                .build();
        assertThrows(HttpClientErrorException.class, () -> buyProduct(buyInput,body.getToken()));
        reset(body.getToken());
    }
}