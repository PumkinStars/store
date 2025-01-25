package com.online.store.Services.Impls;


import com.online.store.Models.Product;
import com.online.store.Models.ProductOptionImage;
import com.online.store.Repositories.ProductOptionImageRepository;
import com.online.store.Services.ProductOptionImageService;
import com.online.store.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOptionImageServiceImpl implements ProductOptionImageService {
    @Autowired
    ProductOptionImageRepository productOptionImageRepository;
    @Autowired
    ProductService productService;


    @Override
    public ProductOptionImage saveOptionImage(Long productId, String pictureUrl) {
        Optional<Product> product = productService.findById(productId);


        ProductOptionImage productOptionImage = new ProductOptionImage();
        product.ifPresent(value ->  {
            productOptionImage.setProduct(product.get());
            productOptionImage.setPictureUrl(pictureUrl);
        });

        return productOptionImageRepository.save(productOptionImage);
    }

    @Override
    public Optional<ProductOptionImage> findById(Long id) {
        return productOptionImageRepository.findById(id);
    }

    @Override
    public List<ProductOptionImage> findAllByProductId(Long productId) {
        return productOptionImageRepository.findAllByProductId(productId);
    }
}
