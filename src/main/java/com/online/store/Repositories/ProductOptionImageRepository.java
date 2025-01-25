package com.online.store.Repositories;

import com.online.store.Models.ProductOptionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductOptionImageRepository extends JpaRepository<ProductOptionImage, Long> {
    Optional<ProductOptionImage> findByProductId(Long id);
    List<ProductOptionImage> findAllByProductId(Long id);
}
