package com.online.store.Controllers;


import com.online.store.Models.UserEntity;
import com.online.store.Security.IAuthenticationFacade;
import com.online.store.Services.CartItemService;
import com.online.store.Services.UserEntityService;
import com.online.store.Utility.CartDatabaseMessages;
import com.online.store.Utility.GlobalEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartItemService cartItemService;
    @Autowired
    UserEntityService userEntityService;
    @Autowired
    IAuthenticationFacade authenticationFacade;

    @PostMapping("/add/{id}")
    public String addItemToCart(@PathVariable("id") Long productId) {
        Authentication authedUser = authenticationFacade.getAuthentication();
        Optional<UserEntity> user = userEntityService.findByUsername(authedUser.getName());

        if (user.isEmpty()) {
            return "redirect:/" + "products/" + productId.toString();
        }

        cartItemService.addToCart(user.get().getId(), productId);
        return "redirect:/" + "products/" + productId.toString();
    }

    @PostMapping("/remove/{id}")
    public String removeItemFromCart(@PathVariable("id") Long productId) {
        Authentication authedUser = authenticationFacade.getAuthentication();
        Optional<UserEntity> user = userEntityService.findByUsername(authedUser.getName());
        String returnToProductEndpoint = "redirect:/" + "products/" + productId.toString();

        if (user.isEmpty()) {
            return returnToProductEndpoint;
        }

        cartItemService.removeFromCart(user.get().getId(), productId);

        return returnToProductEndpoint;
    }
}
