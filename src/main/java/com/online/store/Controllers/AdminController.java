package com.online.store.Controllers;


import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserEntityService userEntityService;

    @GetMapping
    public String viewAdminPanel(Model model) {
        List<UserEntityDto> allUsers = userEntityService.findAll();
        model.addAttribute("allUsers", allUsers);
        return "admin-panel"; ///TODO: add an admin panel page
    }
}
