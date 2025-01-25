package com.online.store.Controllers;


import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Security.IAuthenticationFacade;
import com.online.store.Services.CartItemService;
import com.online.store.Services.UserEntityService;
import com.online.store.Utility.CartDatabaseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
@Secured("ROLE_CUSTOMER")
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
        Optional<UserEntityDto> user = userEntityService.findByEmail(authedUser.getName());
    
        if (user.isEmpty()) {
            return "redirect:/" + "products/" + productId.toString() + "?unauthenticated";
        }

        CartDatabaseMessages addingMessage = cartItemService.addToCart(user.get().getId(), productId);

        if(addingMessage.equals(CartDatabaseMessages.ADD_SUCCESS)) {
            return "redirect:/" + "products/" + productId.toString() + "?cartAddSuccess";
        }
        return "redirect:/" + "products/" + productId.toString() + "?cartError";
    }

    @PostMapping("/remove/{id}")
    public String deleteItemFromCart(@PathVariable("id") Long productId) {
        Authentication authedUser = authenticationFacade.getAuthentication();
        Optional<UserEntityDto> user = userEntityService.findByEmail(authedUser.getName());
        String returnToProductEndpoint = "redirect:/" + "cart/" + "view";

        if (user.isEmpty()) {
            return returnToProductEndpoint;
        }

        cartItemService.removeFromCart(user.get().getId(), productId);
        return returnToProductEndpoint;
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        Authentication authedUser = authenticationFacade.getAuthentication();
        Optional<UserEntityDto> user = userEntityService.findByEmail(authedUser.getName());

        List<ProductDto> cartItems = cartItemService.getAllCartItems(user.get().getId());
        if(cartItems.isEmpty()) {
            model.addAttribute("emptyCart", true);
            return "view-cart";
        }

        Double cartTotal = 0.0;
        for (int i = 0; i < cartItems.size(); i++) {
            cartTotal += cartItems.get(i).getPrice();
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        return "view-cart";
    }
}
