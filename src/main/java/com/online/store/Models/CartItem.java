package com.online.store.Models;


import jakarta.persistence.*;
import lombok.*;


@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private UserEntity user;
    @OneToOne
    private Product product;
}
