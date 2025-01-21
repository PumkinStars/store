package com.online.store.Controllers;


import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Models.UserEntity;
import com.online.store.Services.UserEntityService;
import com.online.store.Utility.GlobalEndpoints;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class RegistrationController {
    @Autowired
    UserEntityService userEntityService;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        UserEntityDto userDto = new UserEntityDto();
        model.addAttribute("userDto", userDto);
        return GlobalEndpoints.REGISTER.toString();
    }

    @PostMapping("register/save")
    public String saveUser(
            @Valid @ModelAttribute("userDto") UserEntityDto userDto,
            Model model,
            BindingResult result,
            Errors errors) {
        Optional<UserEntity> duplicateUser =
                userEntityService.findByEmail(userDto.getEmail());

        if(duplicateUser.isPresent() || result.hasErrors() || errors.hasErrors()) {
            return GlobalEndpoints.REGISTER_FAIL.toString();
        }

        userEntityService.saveUser(userDto);
        return GlobalEndpoints.LOGIN.toString();
    }


    @GetMapping("/login")
    public String getLogin(Model model) {
        UserEntityDto userObj = new UserEntityDto();
        model.addAttribute("userObj", userObj);
        return GlobalEndpoints.LOGIN.toString();
    }

    @PostMapping("/login/validate")
    public String validateLogin(
            @Valid @ModelAttribute("userObj") UserEntityDto userObj, BindingResult result) {
        Optional<UserEntity> existingUser = userEntityService.findByEmail(userObj.getEmail());

        if(result.hasErrors() || existingUser.isEmpty()) {
            return GlobalEndpoints.LOGIN_FAIL.toString();
        }
        return GlobalEndpoints.HOME_REDIRECT.toString();
    }
}
