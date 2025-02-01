package com.online.store.Services.Impls;


import com.online.store.Models.CartItem;
import com.online.store.Models.Dtos.ProductRequest;
import com.online.store.Models.Dtos.StripeResponse;
import com.online.store.Services.PaymentService;
import com.online.store.Services.ProductService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ProductService productService;

    @Override
    // May throw a StripeException, handled in the controller.
    public StripeResponse checkoutProducts(List<ProductRequest> productRequest) throws StripeException {
        List<SessionCreateParams.LineItem> itemsList = productRequest
                .stream()
                .map(this::buildLineItem)
                .toList();
        SessionCreateParams sessionCreateParams = buildSessionParams(itemsList);
        Session session = Session.create(sessionCreateParams);

        session.setExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS).getEpochSecond());
        return StripeResponse.builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
    public List<ProductRequest> productsToProductRequests(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> ProductRequest.builder()
                      .name(cartItem.getProduct().getName())
                      .price((long) (cartItem.getProduct().getPrice() * 100))
                      .quantity(productService.getAmountFromAvailableStock(cartItem.getProduct(), cartItem.getQuantity()))
                      .currency("USD")
                      .pictureUrl(cartItem.getProduct().getPictureUrl())
                      .build())
                .toList();
    }

    private SessionCreateParams.LineItem buildLineItem(ProductRequest productRequest) {
        String productCurrency = productRequest.getCurrency() == null ? "USD": productRequest.getCurrency();

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.getName())
                .addImage(productRequest.getPictureUrl())
                .build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productCurrency)
                .setUnitAmount(productRequest.getPrice())
                .setProductData(productData)
                .build();

        return SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.getQuantity())
                .setPriceData(priceData)
                .build();
    }

    private SessionCreateParams buildSessionParams(List<SessionCreateParams.LineItem> lineItems) {
        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8090/api/payment/success")
                .setCancelUrl("http://localhost:8090/api/payment/fail")
                .addAllLineItem(lineItems)
                .build();
    }
}

