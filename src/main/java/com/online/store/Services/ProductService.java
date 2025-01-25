package com.online.store.Services;


import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> findAll();
    Optional<ProductDto> findByIdAsDto(Long id);
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    Optional<ProductDto> findByNameAsDto(String name);
    Product saveProduct(ProductDto productDto);
    ProductDto productToDto(Product product);
    Product dtoToProduct(ProductDto productDto);
}
