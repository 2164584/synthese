package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.openqa.selenium.By;
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
