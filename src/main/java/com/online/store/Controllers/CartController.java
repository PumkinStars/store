package com.online.store.Controllers;


import com.online.store.Models.CartItem;
import com.online.store.Models.Dtos.CartItemDto;
import com.online.store.Models.Product;
import com.online.store.Models.UserEntity;
import com.online.store.Repositories.CartItemRepository;
import com.online.store.Repositories.ProductRepository;
import com.online.store.Repositories.UserEntityRepository;
import com.online.store.Services.CartItemService;
import com.online.store.Services.ProductService;
import com.online.store.Utility.GlobalEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/cart")
public class CartController {

}
