package com.online.store.Services;


import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Utility.CartDatabaseMessages;

import java.util.List;

public interface CartItemService {
    CartDatabaseMessages addToCart(Long userId, Long productId);
    CartDatabaseMessages removeFromCart(Long userId, Long productId);
    List<ProductDto> getAllCartItems(Long userId);
}
