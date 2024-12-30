package com.online.store.Controllers;


import com.online.store.Models.UserEntity;
import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Services.UserEntityService;
import com.online.store.Utility.GlobalEndpoints;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class RegistrationController {
    @Autowired
    UserEntityService userEntityService;

    @PostMapping("/register/user")
    public @ResponseBody UserEntity saveUser(@RequestBody UserEntityDto user) {
        return userEntityService.saveUser(user);
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        UserEntityDto userDto = new UserEntityDto();
        model.addAttribute("userDto", userDto);
        return GlobalEndpoints.REGISTER.toString();
    }

    @PostMapping
    public String saveUser(
            @Valid @ModelAttribute("userDto") UserEntityDto userDto,
            Model model,
            BindingResult result) {
        Optional<UserEntity> duplicateUser =
                userEntityService.findByEmail(userDto.getEmail());

        if(duplicateUser.isPresent()) {
            return GlobalEndpoints.REGISTER_FAIL.toString();
        } else if(result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return GlobalEndpoints.REGISTER.toString();
        }

        userEntityService.saveUser(userDto);
        return GlobalEndpoints.HOME_REDIRECT.toString();
    }


    @GetMapping("/login")
    public String getLogin(Model model) {
        UserEntity userObj = new UserEntity();
        model.addAttribute("userObj", userObj);
        return GlobalEndpoints.LOGIN.toString();
    }

    @PostMapping("/login/validate")
    public String validateLogin(
            @Valid @ModelAttribute("userObj") UserEntity userObj,
            BindingResult result) {
        Optional<UserEntity> existingUser = userEntityService
                .findByUsername(userObj.getUsername());

        if(result.hasErrors() || existingUser.isEmpty()) {
            return GlobalEndpoints.LOGIN_FAIL.toString();
        }
        return GlobalEndpoints.HOME_REDIRECT.toString();
    }
}
