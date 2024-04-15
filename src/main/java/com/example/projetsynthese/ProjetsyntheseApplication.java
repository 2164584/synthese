package com.example.projetsynthese;

import com.example.projetsynthese.callAPI.SuperC;
import com.example.projetsynthese.repository.SuperCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ProjetsyntheseApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjetsyntheseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Running...");
	}

}
