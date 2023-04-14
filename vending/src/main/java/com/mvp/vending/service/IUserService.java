package com.mvp.vending.service;

import com.mvp.vending.data.BoughtProduct;
import com.mvp.vending.data.BuyInput;
import com.mvp.vending.data.LoginInput;
import com.mvp.vending.data.UserDeposit;
import com.mvp.vending.data.entity.Users;

public interface IUserService {
    Long addUser(Users users);

    Float addDeposit(UserDeposit userDeposit);

    Float resetDeposit();

    Users getUserDetails(LoginInput loginInput);

    BoughtProduct buyProduct(BuyInput buyInput);

    Users getUserById(Long userId);
}
