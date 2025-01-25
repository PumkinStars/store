package com.online.store.Controllers;

import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Models.Product;
import com.online.store.Services.ProductService;
import com.online.store.Services.UserEntityService;
import com.online.store.Utility.GlobalEndpoints;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/products")
@RolesAllowed({ "ROLE_SELLER", "ROLE_CUSTOMER" })
@Controller
public class SellerController {
    @Autowired
    UserEntityService userEntityService;
    @Autowired
    ProductService productService;

    @GetMapping("test")
    public String tester() {
        return "Testing text!";
    }
    @GetMapping("/new")
    public String newProduct(Model model) {
        ProductDto newProduct = new ProductDto();
        model.addAttribute("newProduct", newProduct);
        return GlobalEndpoints.ADD_PRODUCT.toString();
    }

    // The class requires authentication before using its methods so the seller.get() call will never be null
    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute("newProduct") ProductDto newProduct,
            BindingResult result,
            Model model, Principal principal) {
        Optional<UserEntityDto> seller = userEntityService.findByEmail(principal.getName());
        long productOwner = newProduct.getSeller().getId();

        if (!Objects.equals(seller.get().getId(), productOwner)) return GlobalEndpoints.FORBIDDEN_ERROR.toString();

        Optional<Product> existingProduct = productService.findByName(newProduct.getName());

        if (existingProduct.isPresent()) {
            result.reject("DuplicateProductSubmission", "Error: Product already exists");
        } else if (result.hasErrors())
        {
            model.addAttribute("newProduct", newProduct);
            return GlobalEndpoints.ADD_PRODUCT.toString();
        }

        newProduct.setSeller(userEntityService.dtoToUserEntity(seller.get()));
        productService.saveProduct(newProduct);
        return GlobalEndpoints.ADD_PRODUCT.toString();
    }

    @GetMapping("/edit/{id}")
    public String getProductToEdit(@PathVariable Long id, Model model, Principal principal) {
        Optional<Product> productToEdit = productService.findById(id);

        if(productToEdit.isEmpty()) {
            return GlobalEndpoints.HOME.toString();
        } else if (!Objects.equals(productToEdit.get().getSeller().getEmail(), principal.getName())) {
            return GlobalEndpoints.FORBIDDEN_ERROR.toString();
        }

        model.addAttribute("productToEdit", productService.productToDto(productToEdit.get()));
        return GlobalEndpoints.EDIT_PRODUCT.toString();
    }

    @PostMapping("/edit/save/{id}")
    public String saveEditedProduct(
            Model model,
            @Valid @ModelAttribute("productToEdit") ProductDto editedProduct,
            BindingResult result, Principal principal) {
        Optional<UserEntityDto> seller = userEntityService.findByEmail(principal.getName());

        if (!Objects.equals(seller.get().getId(), editedProduct.getSeller().getId())) return GlobalEndpoints.HOME.toString();

        if(result.hasErrors()) {
            model.addAttribute("newProduct", editedProduct);
            return GlobalEndpoints.EDIT_PRODUCT.toString();
        }

        editedProduct.setSeller(userEntityService.dtoToUserEntity(seller.get()));
        productService.saveProduct(editedProduct);
        return GlobalEndpoints.BASE_REDIRECT.toString();
    }
}