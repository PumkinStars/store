package com.online.store.Models.Dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductRequest {
    private Long price;
    private Long quantity;
    private String name;
    private String pictureUrl;
    private String currency;
}
