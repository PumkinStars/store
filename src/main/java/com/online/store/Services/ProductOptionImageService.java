package com.online.store.Services;


import com.online.store.Models.ProductOptionImage;

import java.util.List;
import java.util.Optional;

public interface ProductOptionImageService {
    ProductOptionImage saveOptionImage(Long productId, String pictureUrl);
    Optional<ProductOptionImage> findById(Long id);
    List<ProductOptionImage> findAllByProductId(Long productId);
}
