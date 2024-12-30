package com.online.store.Repositories;


import com.online.store.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Modifying
    @Query("delete from CartItem c where c.user=:userId AND c.product=:productId")
    Integer deleteCartItem(@Param("userId") Long userId, @Param("productId") Long productId);
}
