package com.online.store.Controllers;

import com.online.store.Models.Product;
import com.online.store.Services.ProductService;
import com.online.store.Utility.GlobalEndpoints;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;


    @GetMapping("admin/new")
    public String newProduct(Model model) {
        Product newProduct = new Product();
        model.addAttribute("newProduct", newProduct);
        return GlobalEndpoints.ADD_PRODUCT.toString();
    }

    @PostMapping("admin/save")
    public String saveProduct(
            @Valid @ModelAttribute("newProduct") Product newProduct,
            BindingResult result,
            Model model) {

        Optional<Product> existingProduct = productService.findByName(newProduct.getName());

        if (existingProduct.isPresent()) {
            result.reject("DuplicateProductSubmission", "Error: Product already exists");
        }

        if(result.hasErrors())
        {
            model.addAttribute("newProduct", newProduct);
            return GlobalEndpoints.ADD_PRODUCT.toString();
        }

        productService.saveProduct(newProduct);
        return GlobalEndpoints.ADD_PRODUCT.toString();
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model) {
        Optional<Product> productToView = productService.findById(id);

        if(productToView.isEmpty()) {
            return GlobalEndpoints.HOME.toString();
        } else {
            model.addAttribute("productToView", productToView.get());
            return GlobalEndpoints.VIEW_PRODUCT_DETAILS.toString();
        }

    }

    @GetMapping("admin/edit/{id}")
    public String getProductToEdit(@PathVariable Long id, Model model) {
        Optional<Product> productToEdit = productService.findById(id);

        if(productToEdit.isEmpty()) {
            return GlobalEndpoints.HOME.toString();
        } else {
            model.addAttribute("productToEdit", productToEdit.get());
            return GlobalEndpoints.EDIT_PRODUCT.toString();
        }
    }

    @PostMapping("admin/edit/save/{id}")
    public String saveEditedProduct(
            @PathVariable Long id,
            Model model,
            @Valid @ModelAttribute("productToEdit") Product editedProduct,
            BindingResult result) {

        if(result.hasErrors()) {
            model.addAttribute("newProduct", editedProduct);
            return GlobalEndpoints.EDIT_PRODUCT.toString();
        }

        productService.saveProduct(editedProduct);
        return GlobalEndpoints.HOME_REDIRECT.toString();
    }
}