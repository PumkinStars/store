package com.online.store.Models.Dtos;


import com.online.store.Models.Product;
import com.online.store.Models.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long userId;
    private Long productId;
}
