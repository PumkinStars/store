package com.online.store.Services;


import com.online.store.Models.UserEntity;
import com.online.store.Utility.CartDatabaseMessages;
import javassist.NotFoundException;

import java.util.Optional;

public interface CartItemService {
    CartDatabaseMessages addToCart(Long userId, Long productId);
    CartDatabaseMessages removeFromCart(Long userId, Long productId);
}
