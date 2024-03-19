package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Metro {
    public final String URL = "https://www.metro.ca/fr/epicerie-en-ligne/recherche";

    private List<String> categories = new ArrayList<>();
    private List<Product> produits = new ArrayList<>();

    private final int NB_MAX_PAGE = 200;

    public Metro() {
        getDatas();
    }

    private void getDatas() {
        WebDriver driver1 = new ChromeDriver();
        driver1.get(URL);
        WebElement categories = driver1.findElement(By.cssSelector("div.accordion--text"));

        System.out.println("Text: " + categories.findElements(By.cssSelector("div")).size());
        driver1.quit();


        /*for (int i = 0; i < NB_MAX_PAGE; i++){
            WebDriver driver = new ChromeDriver();
            if (i != 0) {
                driver.get(URL + "-page-" + (i + 1));
            } else {
                driver.get(URL);
            }

            transferToArray(driver);
            driver.quit();
        }*/
    }

    private void transferToArray(WebDriver driver){
        List<Product> tempList = new ArrayList<>();
        List<WebElement> productTiles = driver.findElements(By.cssSelector("div.default-product-tile"));
        for (WebElement productTile : productTiles) {
            String name = productTile.findElement(By.cssSelector("div.head__title")).getText();
            String price = productTile.findElement(By.cssSelector("span.price-update")).getText();

            tempList.add(new Product(name, price));
        }

        synchronized (produits){
            produits.addAll(tempList);
        }
        System.out.println(produits.size());
    }
}
