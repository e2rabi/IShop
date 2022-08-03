package com.errabi.ishop;

import com.errabi.ishop.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationApplication {

	Product product = new Product();
	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
	}

}
