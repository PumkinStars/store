package com.online.store.Models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Column(length = 1024)
    private String description;
    @Min(1)
    private Double price;
    @Min(1)
    private Integer availableAmount;
    @NotBlank
    @Column(name = "pictureUrl", length = 1024)
    private String pictureUrl;

}
