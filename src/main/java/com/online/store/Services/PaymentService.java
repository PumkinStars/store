package com.online.store.Services;

import com.online.store.Models.CartItem;
import com.online.store.Models.Dtos.ProductRequest;
import com.online.store.Models.Dtos.StripeResponse;
import com.stripe.exception.StripeException;

import java.util.List;

public interface PaymentService {
    StripeResponse checkoutProducts(List<ProductRequest> productRequest) throws StripeException;
    List<ProductRequest> productsToProductRequests(List<CartItem> cartItems);
}
