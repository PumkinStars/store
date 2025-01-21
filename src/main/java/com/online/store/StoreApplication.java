package com.online.store;

import com.github.javafaker.Faker;
import com.online.store.Models.Dtos.ProductDto;
import com.online.store.Models.Dtos.UserEntityDto;
import com.online.store.Services.ProductService;
import com.online.store.Services.UserEntityService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;


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
			double price = Math.random() * 100;
			DecimalFormat numberFormat = new DecimalFormat("#.00");
			price = Double.parseDouble(numberFormat.format(price));
			Integer availAmount = (int) Math.floor(Math.random() * 100);

			ProductDto product = ProductDto.builder()
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
				.roles("ADMIN,CUSTOMER")
				.build();
		UserEntityDto user2 = UserEntityDto.builder()
				.username("username")
				.email("kolobooi5@gmail.com")
				.password("123")
				.roles("CUSTOMER")
				.build();
		userEntityService.saveUser(user);
		userEntityService.saveUser(user2);
	}
}
