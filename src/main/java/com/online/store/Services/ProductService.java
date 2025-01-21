package com.online.store.Services;


import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    void saveProduct(ProductDto productDto);
    ProductDto productToDto(Product product);
    Product dtoToProduct(ProductDto productDto);
}
