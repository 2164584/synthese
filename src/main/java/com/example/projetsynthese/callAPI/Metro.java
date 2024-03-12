package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class Metro {
    public final String URL = "https://www.metro.ca/epicerie-en-ligne/recherche";
    private List<Product> produits = new ArrayList<>();

    private final int NB_MAX_PAGE = 375;

    public Metro() throws InterruptedException {
        getDatas();
    }

    private void getDatas() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.metro.ca/fr/epicerie-en-ligne/recherche");

        Thread.sleep(2000);

        String pageSource = driver.getPageSource();

        System.out.println(pageSource);

        driver.quit();
        // Add more parsing logic as needed
    }
}
