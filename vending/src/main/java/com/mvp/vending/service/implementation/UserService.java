package com.mvp.vending.service.implementation;

import com.mvp.vending.data.BoughtProduct;
import com.mvp.vending.data.BuyInput;
import com.mvp.vending.data.LoginInput;
import com.mvp.vending.data.UserDeposit;
import com.mvp.vending.data.entity.Product;
import com.mvp.vending.data.entity.Users;
import com.mvp.vending.repository.ProductRepository;
import com.mvp.vending.repository.UserRepository;
import com.mvp.vending.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    @Override
    public Long addUser(Users users) {
        Optional<Users> foundUser = userRepository.findByUsername(users.getUsername());
        foundUser.ifPresent(x -> {throw new IllegalArgumentException(x.getUsername() + "already exists");});
        Users savedUsers = userRepository.save(users);
        return savedUsers.getId();
    }

    @Override
    public Float addDeposit(UserDeposit userDeposit) {
        Users foundUsers = retrieveContextUser();
        Float amountAdded = sumAmount(userDeposit);
        foundUsers.setDeposit(foundUsers.getDeposit() + amountAdded);
        Users save = userRepository.save(foundUsers);
        return save.getDeposit();
    }

    private Users retrieveContextUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users foundUsers = userRepository.findByUsername(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        return foundUsers;
    }

    private Float sumAmount(UserDeposit userDeposit) {
        Integer moneyAddedInCents = userDeposit.getFiveCents() * 5
                + userDeposit.getTenCents() * 10
                + userDeposit.getTwentyCents() * 20
                + userDeposit.getFiftyCents() * 50
                + userDeposit.getHundredCents() * 100;
        return (float) (moneyAddedInCents/100F);
    }

    @Override
    public Float resetDeposit() {
        Users foundUsers = retrieveContextUser();
        foundUsers.setDeposit(0F);
        Users save = userRepository.save(foundUsers);
        return save.getDeposit();
    }

    @Override
    public Users getUserDetails(LoginInput loginInput) {
        return userRepository.findByUsername(loginInput.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public BoughtProduct buyProduct(BuyInput buyInput) {
        Product foundProduct = productRepository.findById(buyInput.getProductId()).orElseThrow();
        Users users = retrieveContextUser();
        float depositLeft = getDepositLeft(foundProduct.getCost() * buyInput.getAmountPurchased(), users);
        foundProduct.setAmountAvailable(foundProduct.getAmountAvailable() - buyInput.getAmountPurchased());
        if(!(depositLeft >= 0 && foundProduct.getAmountAvailable() >= 0)) {
            throw new IllegalArgumentException("There is not enough product available or you do not have enough saldo");
        }
        users.setDeposit(depositLeft);
        List<Integer> changeMoney = retrieveChangeMoney(depositLeft);
        userRepository.save(users);
        productRepository.save(foundProduct);
        return BoughtProduct.builder()
                .productName(foundProduct.getName())
                .depositLeft(depositLeft)
                .moneySpent(foundProduct.getCost() * buyInput.getAmountPurchased())
                .changeMoney(changeMoney)
                .build();
    }

    private float getDepositLeft(Float spent, Users users) {
        return (Math.round(users.getDeposit()*100) - Math.round(spent*100))/100F;
    }

    private List<Integer> retrieveChangeMoney(float depositLeft) {
        Integer changeInCents = (int) (depositLeft * 100);
        return calculateChange( changeInCents);
    }

    private List calculateChange(Integer changeInCents) {

        List<Integer> result = new ArrayList<>();
        changeInCents = addCoinValueToChangeArray(result, changeInCents, 100);
        changeInCents = addCoinValueToChangeArray(result, changeInCents, 50);
        changeInCents = addCoinValueToChangeArray(result, changeInCents, 20);
        changeInCents = addCoinValueToChangeArray(result, changeInCents, 10);
        changeInCents = addCoinValueToChangeArray(result, changeInCents, 5);
        if(changeInCents > 0) {
            throw new IllegalStateException("There was an error calculating the change");
        }
        return result;
    }

    private Integer addCoinValueToChangeArray(List<Integer> result, Integer changeInCents, Integer coinValue) {
        if(changeInCents % coinValue >= 0) {
            int numberOfCoins = changeInCents / coinValue;
            for(int i = 0; i < numberOfCoins; i++) {
                result.add(coinValue);
            }
            changeInCents = changeInCents - (numberOfCoins * coinValue);
        }
        return changeInCents;
    }

    @Override
    public Users getUserById(Long userId) {
        Users users = userRepository.findById(userId).orElseThrow();
        return users;
    }

}
