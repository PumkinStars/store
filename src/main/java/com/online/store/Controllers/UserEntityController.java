package com.online.store.Controllers;


import com.online.store.Models.UserEntity;
import com.online.store.Services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserEntityController {
    @Autowired
    UserEntityService userEntityService;

    @PostMapping("/register/user")
    public @ResponseBody UserEntity saveUser(@RequestBody UserEntity user) {
        userEntityService.createUser(user);
        return user;
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        UserEntity userObj = new UserEntity();
        model.addAttribute("userObj", userObj);
        return "login";
    }

    @PostMapping("/login/validate")
    public String validateLogin(
            @Valid @ModelAttribute("userObj") UserEntity userObj,
            BindingResult result) {
        if(result.hasErrors()) {
            return "login";
        } else {
            return "redirect:/";
        }
    }
}
