package com.online.store.Services;

import com.online.store.Models.CartItem;
import com.online.store.Models.Product;
import com.online.store.Models.UserEntity;
import com.online.store.Utility.CartDatabaseMessages;
import com.online.store.Utility.SuccessMessage;
import javassist.NotFoundException;

public interface CartItemService {
    CartDatabaseMessages addToCart(Long userId, Long productId);
    CartDatabaseMessages removeFromCart(Long userId, Long productId) throws NotFoundException;
}
