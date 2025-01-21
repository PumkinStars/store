package com.online.store.Services.Impls;


import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Models.Product;
import com.online.store.Repositories.ProductRepository;
import com.online.store.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> findAll() {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .map(this::productToDto)
                .toList();
    }
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void saveProduct(ProductDto productdto) {

        productRepository.save(dtoToProduct(productdto));
    }

    @Override
    public ProductDto productToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .pictureUrl(product.getPictureUrl())
                .availableAmount(product.getAvailableAmount())
                .build();
    }

    @Override
    public Product dtoToProduct(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .availableAmount(productDto.getAvailableAmount())
                .pictureUrl(productDto.getPictureUrl())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();
    }

}