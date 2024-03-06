package com.example.projetsynthese;

import com.example.projetsynthese.callAPI.SuperC;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProjetsyntheseApplication {

	public static void main(String[] args) throws IOException {
		SuperC superC = new SuperC();
	}

}
