package com.abhinash.shoppingdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:2200")
public class ShoppingDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingDashboardApplication.class, args);
	}

}
