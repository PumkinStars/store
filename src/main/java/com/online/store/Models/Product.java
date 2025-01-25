package com.online.store.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    ///TODO: Cascade removing a user to a product so that it also gets deleted.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity_id", nullable = false)
    private UserEntity seller;
    @NotBlank
    private String name;
    @Column(length = 1024)
    private String description;
    private Double price;
    @Min(1)
    private Integer availableAmount;
    @NotBlank
    @Column(name = "pictureUrl", length = 1024)
    private String pictureUrl;
}
