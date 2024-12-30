package com.online.store;

import com.github.javafaker.Faker;
import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Models.Product;
import com.online.store.Models.UserEntity;
import com.online.store.Repositories.ProductRepository;
import com.online.store.Repositories.UserEntityRepository;
import com.online.store.Services.ProductService;
import com.online.store.Services.UserEntityService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@SpringBootApplication
public class StoreApplication {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserEntityService userEntityService;

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}


	@PostConstruct
	public void initTestData() {
		Faker faker = new Faker();
		String pictureUrl = "https://i.etsystatic.com/26201936/r/il/a1c097/6047430430/il_600x600.6047430430_fujk.jpg";

		for(int i = 0; i < 5; i++) {
			String name = faker.artist().name();
			String description = faker.gameOfThrones().quote();
			Double price = Math.random() * 100;
			Integer availAmount = Math.toIntExact(Math.round(Math.random() * 100));

			Product product = Product.builder()
					.name(name)
					.description(description)
					.price(price)
					.pictureUrl(pictureUrl)
					.availableAmount(availAmount)
					.build();
			productService.saveProduct(product);
		}
		UserEntityDto user = UserEntityDto.builder()
				.username("PumkinStars")
				.email("blah@gmail.com")
				.password("123")
				.roles("ADMIN,USER")
				.build();
		userEntityService.saveUser(user);
	}
}
