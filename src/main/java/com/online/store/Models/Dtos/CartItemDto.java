package com.online.store.Models.Dtos;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private Long userId;
    private Long productId;
}
