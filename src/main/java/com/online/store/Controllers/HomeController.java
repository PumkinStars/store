package com.online.store.Controllers;


import com.online.store.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class HomeController {
    @Autowired
    ProductService productService;

    @GetMapping
    public String getHome(Model model) {
        model.addAttribute("products", productService.findAll());
        return "home";
    }
}
