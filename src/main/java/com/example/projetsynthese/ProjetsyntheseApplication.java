package com.example.projetsynthese;

import com.example.projetsynthese.callAPI.Metro;
import com.example.projetsynthese.callAPI.SuperC;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ProjetsyntheseApplication {

	public static void main(String[] args) throws IOException {
		//SuperC superC = new SuperC();
		Metro metro = new Metro();
	}

}
