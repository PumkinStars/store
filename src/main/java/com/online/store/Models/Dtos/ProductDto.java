package com.online.store.Models.Dtos;

import com.online.store.Models.UserEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductDto {
    private Long id;
    @NotBlank
    private String name;
    private UserEntity seller;
    private String description;
    private Double price;
    @Min(1)
    private Integer availableAmount;
    @NotBlank
    private String pictureUrl;
}
