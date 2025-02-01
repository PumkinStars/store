package com.online.store.Controllers;


import com.online.store.Models.Dtos.CartItemDto;
import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Services.ProductOptionImageService;
import com.online.store.Services.ProductService;
import com.online.store.Utility.GlobalEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping("/")
@Controller
public class HomeController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductOptionImageService productOptionImageService;


    @GetMapping
    public String getHome(Model model) {
        model.addAttribute("products", productService.findAll());
        return "home";
    }

    @GetMapping("/products/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model) {
        Optional<ProductDto> productToView = productService.findByIdAsDto(id);
        CartItemDto cartItemDto = new CartItemDto();
        if(productToView.isEmpty()) return GlobalEndpoints.HOME.toString();

        model.addAttribute("productToView", productToView.get());
        model.addAttribute("cartItemDto", cartItemDto);
        return GlobalEndpoints.VIEW_PRODUCT_DETAILS.toString();
    }
}
