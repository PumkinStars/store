package com.online.store.Controllers;


import com.online.store.Models.CartItem;
import com.online.store.Models.Dtos.ProductRequest;
import com.online.store.Models.Dtos.StripeResponse;
import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Services.CartItemService;
import com.online.store.Services.PaymentService;
import com.online.store.Services.UserEntityService;
import com.online.store.Utility.GlobalEndpoints;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/payment")
@Secured("ROLE_CUSTOMER")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    UserEntityService userEntityService;
    @Autowired
    CartItemService cartItemService;

    @GetMapping("/checkout")
    public String checkoutProducts(Principal principal) {
        for(int i = 0; i < 5;i++) {
            System.out.println("CHECKOUT MESSAGE RECEIVED");
        }
        StripeResponse stripeResponse;
        Optional<UserEntityDto> user = userEntityService.findByEmail(principal.getName());

        List<CartItem> cartItems = cartItemService.getAllCartItems(user.get().getId());

        List<ProductRequest> productRequests = paymentService.productsToProductRequests(cartItems);

        try {
            stripeResponse = paymentService.checkoutProducts(productRequests);
        } catch (StripeException ex) {
            return GlobalEndpoints.STRIPE_TRANSACTION_FAILED.toString();
        }

        System.out.println(stripeResponse.getSessionUrl());

        System.out.println("Ice cream");
        String redirectUrl = stripeResponse.getSessionUrl();
        return "redirect:" + redirectUrl;

    }

    @GetMapping("/success")
    public String paymentSuccess() {
        return "payment-success";
    }

    @GetMapping("/fail")
    public String paymentFailure() {

        return "payment-failed";
    }
}