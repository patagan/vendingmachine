package com.mvp.vending.service.implementation;

import com.mvp.vending.data.ProductInformation;
import com.mvp.vending.data.entity.Product;
import com.mvp.vending.data.entity.Users;
import com.mvp.vending.repository.ProductRepository;
import com.mvp.vending.repository.UserRepository;
import com.mvp.vending.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product newProduct) {
        newProduct.setSellerId(retrieveContextUser().getId());
        Product savedProduct = productRepository.save(newProduct);
        return savedProduct;
    }

    private Users retrieveContextUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users foundUsers = userRepository.findByUsername(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        return foundUsers;
    }

    @Override
    public Product updateProduct(ProductInformation productInformation) {

        Product foundProduct = productRepository.findById(productInformation.getId()).orElseThrow();
        isUserCreatorOfProduct(foundProduct);

        foundProduct.setAmountAvailable(productInformation.getAmountAvailable());
        foundProduct.setCost(productInformation.getCost());
        foundProduct.setName(productInformation.getName());
        return productRepository.save(foundProduct);
    }

    private void isUserCreatorOfProduct(Product foundProduct) {
        Users users = retrieveContextUser();
        if(!users.getId().equals(foundProduct.getSellerId())) {
            throw new IllegalCallerException("You are not the creator of the product " + foundProduct.getName());
        }
    }

    @Override
    public void deleteProduct(Long id) {
        Product foundProduct = productRepository.findById(id).orElseThrow();
        isUserCreatorOfProduct(foundProduct);
        productRepository.deleteById(id);
    }
}
