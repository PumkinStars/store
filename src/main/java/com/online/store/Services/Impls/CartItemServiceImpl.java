package com.online.store.Services.Impls;

import com.online.store.Models.CartItem;
import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Models.Product;
import com.online.store.Models.UserEntity;
import com.online.store.Repositories.CartItemRepository;
import com.online.store.Repositories.UserEntityRepository;
import com.online.store.Services.CartItemService;
import com.online.store.Services.ProductService;
import com.online.store.Utility.CartDatabaseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    ProductService productService;


    @Override
    public CartDatabaseMessages addToCart(Long userId, Long productId) {
        Optional<UserEntity> user = userEntityRepository.findById(userId);

        if(user.isEmpty()) {
            return CartDatabaseMessages.USER_NOT_FOUND;
        }

        Optional<Product> product = productService.findById(productId);
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
        long itemDeleted = cartItemRepository.deleteByUserIdAndProductId(userId, productId);

        if (itemDeleted == 0) {
            Optional<UserEntity> userIsPresent = userEntityRepository.findById(userId);
            if (userIsPresent.isEmpty()) {
                return CartDatabaseMessages.USER_NOT_FOUND;
            }

            Optional<Product> productIsPresent = productService.findById(productId);
            if (productIsPresent.isEmpty()) {
                return CartDatabaseMessages.PRODUCT_NOT_FOUND;
            }

            return CartDatabaseMessages.USER_AND_PRODUCT_NOT_FOUND;
        }

        return CartDatabaseMessages.DELETE_SUCCESS;
    }

    @Override
    public List<ProductDto> getAllCartItems(Long userId) {
        List<CartItem> cartItemIds = cartItemRepository.findAllByUserId(userId);
        System.out.println(cartItemIds.size());
        List<ProductDto> userCartItems = new ArrayList<>();

        for (CartItem cartItemId : cartItemIds) {
            Optional<Product> itemFromProductId = productService.findById(cartItemId.getProduct().getId());

            if(itemFromProductId.isEmpty()){
                continue;
            }
            userCartItems.add(productService.productToDto(itemFromProductId.get()));
        }

        return userCartItems;
    }
}
