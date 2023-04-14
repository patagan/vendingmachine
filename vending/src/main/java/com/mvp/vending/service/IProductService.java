package com.mvp.vending.service;

import com.mvp.vending.data.ProductInformation;
import com.mvp.vending.data.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> getProducts();
    Product addProduct(Product newProduct);


    Product updateProduct(ProductInformation productInformation);

    void deleteProduct(Long id);
}
