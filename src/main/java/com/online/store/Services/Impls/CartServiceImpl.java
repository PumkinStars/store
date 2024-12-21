package com.online.store.Services.Impls;


import com.online.store.Models.Product;
import com.online.store.Models.UserEntity;
import com.online.store.Repositories.CartRepository;
import com.online.store.Repositories.UserEntityRepository;
import com.online.store.Services.CartService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    UserEntityRepository userEntityRepository;


    @Override
    public void addToCart(UserEntity user, Product product) {
        user.getCart().getCartItems().add(product);
        userEntityRepository.save(user);
    }

    @Override
    public void removeFromCart(UserEntity user, Long productId) {
        user.getCart().setCartItems(
                user.getCart()
                        .getCartItems()
                        .stream()
                        .filter(product -> !Objects.equals(product.getId(), productId))
                        .toList()
        );
        userEntityRepository.save(user);
    }

}
