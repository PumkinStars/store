package com.online.store.Services;


import com.online.store.Models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    void saveProduct(Product product);
}
