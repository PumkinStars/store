package com.online.store.Controllers;

import com.online.store.Models.Product;
import com.online.store.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("products")
public class ProductController {
    static final String ADD_PRODUCT = "add-product";
    static final String EDIT_PRODUCT = "edit-product";
    static final String HOME_ENDPOINT = "redirect:/";

    @Autowired
    ProductService productService;

    private String validateProduct(Product newProduct, Model model, String redirectPoint) {
        Optional<Product> existingProduct = productService.findById(newProduct.getId());

        if(existingProduct.isEmpty()) {
            return "redirect:" + redirectPoint;
        } else {
            model.addAttribute("productToEdit", existingProduct.get());
            return EDIT_PRODUCT;
        }

    }

    @GetMapping("admin/new")
    public String newProduct(Model model) {
        Product newProduct = new Product();
        model.addAttribute("newProduct", newProduct);
        return ADD_PRODUCT;
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
            return ADD_PRODUCT;
        }

        productService.saveProduct(newProduct);
        return ADD_PRODUCT;
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Optional<Product> productToView = productService.findById(id);

        if(productToView.isEmpty()) {
            return HOME_ENDPOINT;
        } else {
            model.addAttribute("productToView", productToView.get());
            return "view-product-details";
        }

    }

    @GetMapping("admin/edit/{id}")
    public String getProductToEdit(@PathVariable Long id, Model model) {
        Optional<Product> productToEdit = productService.findById(id);

        if(productToEdit.isEmpty()) {
            return HOME_ENDPOINT;
        } else {
            model.addAttribute("productToEdit", productToEdit.get());
            return EDIT_PRODUCT;
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
            return EDIT_PRODUCT;
        }

        productService.saveProduct(editedProduct);
        return HOME_ENDPOINT;
    }
}