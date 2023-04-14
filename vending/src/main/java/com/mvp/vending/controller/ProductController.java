package com.mvp.vending.controller;

import com.mvp.vending.data.ProductInformation;
import com.mvp.vending.data.entity.Product;
import com.mvp.vending.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final IProductService iProductService;

    @GetMapping(path = "/getProducts")
    public ResponseEntity<List> getProducts() {

        List<Product> products = iProductService.getProducts();
        return ResponseEntity.ok(products);
    }
    @PostMapping(path = "/addProduct")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Product> addProduct(@RequestBody ProductInformation productInformation) {


        Product newProduct = Product.builder()
                .amountAvailable(productInformation.getAmountAvailable())
                .cost(productInformation.getCost())
                .name(productInformation.getName())
                .build();
        Product addedProduct = iProductService.addProduct(newProduct);
        return ResponseEntity.ok(addedProduct);
    }

    @PutMapping(path = "/updateProduct")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductInformation productInformation) {

        Product product = iProductService.updateProduct(productInformation);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping(path = "/deleteProduct")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Long> deleteProduct(@RequestBody Long id) {

        iProductService.deleteProduct(id);
        return ResponseEntity.ok(id);
    }
}
