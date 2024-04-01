package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class IGA {
    public boolean isDoneFetching = false;
    public final String URL = "https://www.iga.net/fr/search?page=";
    public final String URL_AFTER_PAGE = "&pageSize=24";
    private List<Product> produits = new ArrayList<>();

    public IGA(){}

    public List<Product> getProduits() {
        return produits;
    }

    public void transferToArray(WebDriver driver) {
        List<Product> tempList = new ArrayList<>();
        List<WebElement> productTiles = driver.findElements(By.cssSelector("div.item-product__content"));
        for (WebElement productTile : productTiles) {
            String gram = "";
            String priceDiscount = "";
            String brand = "";
            String pricePerHundGram = "";
            boolean isDiscountedThisWeek = false;
            try {
                gram = driver.findElement(By.cssSelector("div.item-product__info")).getText();
            } catch (NoSuchElementException e) {
                gram = "Unité variable";
            }
            try {
                priceDiscount = driver.findElement(By.cssSelector("div.item-product__price--sale")).getText();
                isDiscountedThisWeek = !priceDiscount.isEmpty();
            } catch (NoSuchElementException e) {
                priceDiscount = "";
            }
            try {
                brand = productTile.findElement(By.cssSelector("div.item-product__brand")).getText();
            } catch (NoSuchElementException e) {
                brand = "";
            }

            String name = productTile.findElement(By.cssSelector("a.js-ga-productname")).getText();
            String price = productTile.findElement(By.cssSelector("div.item-product__price")).getText();
            String image = "https://sbs-prd-cdn-products.azureedge.net/media/image/product/fr/small/" + productTile.findElement(By.cssSelector("a.js-ga-productname")).getAttribute("href").split("_")[1] + ".jpg";
            try {
                pricePerHundGram = productTile.findElement(By.cssSelector("div.text--small")).getText();
            } catch (NoSuchElementException e) {
                pricePerHundGram = price + " / " + gram;
            }
            Product product = new Product(name, image, brand, price, gram, pricePerHundGram, priceDiscount, isDiscountedThisWeek, false);
            tempList.add(product);
        }

        synchronized (produits){
            produits.addAll(tempList);
        }
        System.out.println("IGA: " + produits.size());
    }
}
