package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class Metro {

    public boolean isDoneFetching = false;
    public final String URL = "https://www.metro.ca/epicerie-en-ligne/recherche";
    private List<Product> produits = new ArrayList<>();

    public Metro() {

    }

    public List<Product> getProduits() {
        return produits;
    }

    public void transferToArray(WebDriver driver){
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

            String name = productTile.findElement(By.cssSelector("div.head__title")).getText();
            String price = productTile.findElement(By.cssSelector("span.price-update")).getText();
            String pricePerHundGram = productTile.findElement(By.cssSelector("div.pricing__secondary-price > span:first-child")).getText();
            String image = productTile.findElement(By.cssSelector("img")).getAttribute("src");
            String brand = productTile.findElement(By.cssSelector("span.head__brand")).getText();
            Product product = new Product(name, image, brand, price, gram, pricePerHundGram, priceDiscount, isDiscountedThisWeek, false);
            tempList.add(product);
        }

        synchronized (produits){
            produits.addAll(tempList);
        }
        System.out.println("Metro: " + produits.size());
    }
}
