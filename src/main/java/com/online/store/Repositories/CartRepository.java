package com.online.store.Repositories;


import com.online.store.Models.Cart;
import com.online.store.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
