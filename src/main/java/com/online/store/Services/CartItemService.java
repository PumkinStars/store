package com.online.store.Services;


import com.online.store.Models.CartItem;
import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Utility.CartDatabaseMessages;

import java.util.List;

public interface CartItemService {
    CartDatabaseMessages addToCart(Long userId, Long productId, Long quantity);
    CartDatabaseMessages removeFromCart(Long userId, Long productId);
    List<ProductDto> getAllCartItemsAsProductDtos(Long userId);
    boolean cartItemIsPresent(Long userId, Long productId);
    List<CartItem> getAllCartItems(Long userId);
}
