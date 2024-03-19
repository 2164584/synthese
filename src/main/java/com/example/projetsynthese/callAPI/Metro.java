package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class Metro {
    public final String URL = "https://www.metro.ca/epicerie-en-ligne/recherche";
    private int nbPageMax;
    private List<Product> produits = new ArrayList<>();

    public Metro() {
        getDatas();
    }

    private void getDatas() {

        WebDriver getLastPageDriver = new ChromeDriver();
        getLastPageDriver.get(URL);
        List<WebElement> lastPage = getLastPageDriver.findElements(By.cssSelector("a.ppn--element"));
        nbPageMax = Integer.parseInt(lastPage.get(lastPage.size() - 2).getText());
        getLastPageDriver.quit();

        int nbThread = 8;

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
        System.out.println("fecthing done");
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
