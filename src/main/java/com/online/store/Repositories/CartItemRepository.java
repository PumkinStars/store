package com.online.store.Repositories;


import com.online.store.Models.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Transactional
    long deleteByUserIdAndProductId(Long userId, Long productId);


    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    @Query("select c from CartItem c where c.user.id= ?1")
    List<CartItem> findAllByUserId(Long userId);
}
