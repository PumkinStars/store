package com.online.store.Services;

import com.online.store.Models.Product;
import com.online.store.Models.UserEntity;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface CartService {
    void addToCart(UserEntity user, Product product);
    void removeFromCart(UserEntity user, Long productId);
}
