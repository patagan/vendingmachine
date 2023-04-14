package com.mvp.vending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class VendingApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(VendingApplication.class, args);
	}

}
