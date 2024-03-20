package com.example.projetsynthese;

import com.example.projetsynthese.controller.UserController;
import com.example.projetsynthese.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ProjetsyntheseApplication implements CommandLineRunner {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ProjetsyntheseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserService userService = new UserService();
		userService.getMetroDatas();
		System.out.println("Running...");
	}

}
