package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class IGA {
    public static boolean isDoneFetching = false;
    public static boolean isFetching = false;
    public static final String URL = "https://www.iga.net/fr/search?page=";
    public static final String URL_AFTER_PAGE = "&pageSize=24";
    private static List<Product> produits = new ArrayList<>();

    public IGA(){
        if (!isDoneFetching && !isFetching){
            //getIGADatas();
        }
    }

    public List<Product> getProduits() {
        return produits;
    }

    public static void getIGADatas(){
        isFetching = true;
        int nbPageMax;
        WebDriver getLastPageDriver = new ChromeDriver();
        getLastPageDriver.get(URL + "1" + URL_AFTER_PAGE);
        nbPageMax = Integer.parseInt(getLastPageDriver.findElement(By.cssSelector("a.icon--arrow-skinny-right-double")).getAttribute("href").split("=")[1].split("&")[0]);
        System.out.println(nbPageMax);
        getLastPageDriver.quit();

        int nbThread = 8;


        Thread[] threads = new Thread[nbThread];
        for (int i = 0; i < nbThread; i++){
            final int start = i * (nbPageMax / nbThread);
            final int end = (i == nbThread - 1) ? nbPageMax : start + (nbPageMax / nbThread);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++){
                    String urlToFetch = URL + (j+1) + URL_AFTER_PAGE;
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

        isDoneFetching = true;
        isFetching = false;
    }

    public static void transferToArray(WebDriver driver) {
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
            Product product = new Product(1L, name, image, brand, price, gram, pricePerHundGram, priceDiscount, isDiscountedThisWeek, false);
            tempList.add(product);
        }

        synchronized (produits){
            produits.addAll(tempList);
        }
        System.out.println("IGA: " + produits.size());
    }
}
