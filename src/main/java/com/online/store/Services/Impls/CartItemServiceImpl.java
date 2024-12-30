package com.online.store.Services.Impls;

import com.online.store.Models.CartItem;
import com.online.store.Models.Product;
import com.online.store.Models.UserEntity;
import com.online.store.Repositories.CartItemRepository;
import com.online.store.Repositories.ProductRepository;
import com.online.store.Repositories.UserEntityRepository;
import com.online.store.Services.CartItemService;
import com.online.store.Utility.CartDatabaseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public CartDatabaseMessages addToCart(Long userId, Long productId) {
        Optional<UserEntity> user = userEntityRepository.findById(userId);

        if(user.isEmpty()) {
            return CartDatabaseMessages.USER_NOT_FOUND;
        }

        Optional<Product> product = productRepository.findById(productId);;
        if(product.isEmpty()) {
            return CartDatabaseMessages.PRODUCT_NOT_FOUND;
        }

        CartItem newItem = CartItem.builder()
                .user(user.get())
                .product(product.get())
                .build();

        cartItemRepository.save(newItem);
        return CartDatabaseMessages.ADD_SUCCESS;
    }

    @Override
    public CartDatabaseMessages removeFromCart(Long userId, Long productId) {
        Integer itemDeleted = cartItemRepository.deleteCartItem(userId, productId);

        if (itemDeleted == 0) {
            Optional<UserEntity> userIsPresent = userEntityRepository.findById(userId);
            if (userIsPresent.isEmpty()) {
                return CartDatabaseMessages.USER_NOT_FOUND;
            }

            Optional<Product> productIsPresent = productRepository.findById(productId);
            if (productIsPresent.isEmpty()) {
                return CartDatabaseMessages.PRODUCT_NOT_FOUND;
            }

            return CartDatabaseMessages.USER_AND_PRODUCT_NOT_FOUND;
        }

        return CartDatabaseMessages.DELETE_SUCCESS;
    }
}
