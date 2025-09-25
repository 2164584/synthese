package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.repository.ProductRepository;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class Metro {
    private static ProductRepository productRepository;

    public static boolean isFecthing = false;
    public static final String URL = "https://www.metro.ca/epicerie-en-ligne/recherche";
    @Getter
    private static List<Product> produits = new ArrayList<>();

    @Autowired
    public Metro(ProductRepository productRepository) {
        Metro.productRepository = productRepository;
    }

    public static void getMetroDatas(){
        produits.clear();
        isFecthing = true;
        int nbPageMax;

        WebDriver getLastPageDriver = new ChromeDriver();
        getLastPageDriver.get(URL);
        List<WebElement> lastPage = getLastPageDriver.findElements(By.cssSelector("a.ppn--element"));
        nbPageMax = Integer.parseInt(lastPage.get(lastPage.size() - 2).getText());
        getLastPageDriver.quit();
        System.out.println(nbPageMax);

        int nbThread = 4;

        Thread[] threads = new Thread[nbThread];
        for (int i = 0; i < nbThread; i++){
            final int start = i * (nbPageMax / nbThread);
            final int end = (i == nbThread - 1) ? nbPageMax : start + (nbPageMax / nbThread);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++){
                    String urlToFetch = URL + "-page-"+j;
                    WebDriver driver = new ChromeDriver();
                    driver.get(urlToFetch);
                    transferToArray(driver);
                    driver.quit();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < nbThread; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isFecthing = false;

        productRepository.saveAll(produits);
        System.out.println("Metro is done." );
    }

    public static void transferToArray(WebDriver driver){
        List<Product> tempList = new ArrayList<>();
        List<WebElement> productTiles = driver.findElements(By.cssSelector("div.default-product-tile"));
        for (WebElement productTile : productTiles) {
            String gram = "";
            String priceDiscount = "";
            boolean isDiscountedThisWeek = false;
            try {
                 gram = driver.findElement(By.cssSelector("span.head__unit-details")).getText();
            } catch (NoSuchElementException e) {
                gram = "Unité variable";
            }
            try {
                priceDiscount = driver.findElement(By.cssSelector("div.promo-price")).getText();
                isDiscountedThisWeek = !priceDiscount.isEmpty();
            } catch (NoSuchElementException e) {
                priceDiscount = "";
            }
            String num = productTile.getAttribute("data-product-code");
            int i = 0;
            while (num.charAt(i) == '0') {
                i++;
            }
            String id = "m" + num.substring(i);
            String name = productTile.findElement(By.cssSelector("div.head__title")).getText();
            String price = productTile.findElement(By.cssSelector("span.price-update")).getText();
            String pricePerHundGram = productTile.findElement(By.cssSelector("div.pricing__secondary-price > span:first-child")).getText();
            String image = productTile.findElements(By.cssSelector("img")).get(1).getAttribute("src");
            String brand = productTile.findElement(By.cssSelector("span.head__brand")).getText();
            String link = "";
            Product product = new Product(id, name, image, brand, price, gram, pricePerHundGram, priceDiscount, isDiscountedThisWeek, false, "Metro", link);
            tempList.add(product);
        }

        synchronized (produits){
            produits.addAll(tempList);
        }
    }
}
