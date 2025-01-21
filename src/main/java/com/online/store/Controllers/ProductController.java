package com.online.store.Controllers;

import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Models.Product;
import com.online.store.Models.UserEntity;
import com.online.store.Security.IAuthenticationFacade;
import com.online.store.Services.ProductService;
import com.online.store.Services.UserEntityService;
import com.online.store.Utility.GlobalEndpoints;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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
    @Autowired
    IAuthenticationFacade authenticationFacade;
    @Autowired
    UserEntityService userEntityService;

    @Secured("ADMIN")
    @GetMapping("/new")
    public String newProduct(Model model) {
        Authentication authedUser = authenticationFacade.getAuthentication();
        Optional<UserEntity> user = userEntityService.findByEmail(authedUser.getName());
        System.out.println(authedUser.getAuthorities());

        ProductDto newProduct = new ProductDto();
        model.addAttribute("newProduct", newProduct);
        return GlobalEndpoints.ADD_PRODUCT.toString();
    }
    @RolesAllowed("ADMIN")
    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute("newProduct") ProductDto newProduct,
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
            model.addAttribute(
                    "productToView",
                    productService.productToDto(productToView.get())
            );
            return GlobalEndpoints.VIEW_PRODUCT_DETAILS.toString();
        }

    }

    @Secured("ADMIN")
    @GetMapping("/edit/{id}")
    public String getProductToEdit(@PathVariable Long id, Model model) {
        Optional<Product> productToEdit = productService.findById(id);

        if(productToEdit.isEmpty()) {
            return GlobalEndpoints.HOME.toString();
        } else {
            model.addAttribute("productToEdit", productService.productToDto(productToEdit.get()));
            return GlobalEndpoints.EDIT_PRODUCT.toString();
        }
    }
    @Secured("ADMIN")
    @PostMapping("/edit/save/{id}")
    public String saveEditedProduct(
            @PathVariable Long id,
            Model model,
            @Valid @ModelAttribute("productToEdit") ProductDto editedProduct,
            BindingResult result) {

        if(result.hasErrors()) {
            model.addAttribute("newProduct", editedProduct);
            return GlobalEndpoints.EDIT_PRODUCT.toString();
        }

        productService.saveProduct(editedProduct);
        return GlobalEndpoints.HOME_REDIRECT.toString();
    }
}