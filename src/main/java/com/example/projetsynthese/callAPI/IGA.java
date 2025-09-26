package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.repository.ProductRepository;
import jakarta.persistence.Id;
import lombok.Getter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class IGA {
    private static ProductRepository productRepository;

    public static boolean isFetching = false;
    public static final String URL = "https://www.iga.net/fr/search?page=";
    public static final String URL_AFTER_PAGE = "&pageSize=24";
    private static int nbOfPagesCompleted = 0;
    @Getter
    private static float percent = 0;
    @Getter
    private static List<Product> produits = new ArrayList<>();

    @Autowired
    public IGA(ProductRepository productRepository){
        IGA.productRepository = productRepository;
    }

    public static float getPercent(){
        return percent;
    }

    public static void getSupercDatas() {
        produits.clear();
        int nbPageMax;
        isFetching = true;

        Connection connectionForPages = Jsoup.connect(URL + "1" + URL_AFTER_PAGE);
        try {
            Document document = connectionForPages.get();
            try (FileWriter writer = new FileWriter("src/main/java/com/example/projetsynthese/resultIGA.html")) {
                writer.write(document.html());
            }
            nbPageMax = Integer.parseInt(document.select("a.icon--arrow-skinny-right-double").attr("href").split("=")[1].split("&")[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(nbPageMax);

        int nbThread = 4;

        Thread[] threads = new Thread[nbThread];
        for (int i = 0; i < nbThread; i++){
            final int start = i * (nbPageMax / nbThread);
            final int end = (i == nbThread - 1) ? nbPageMax : start + (nbPageMax / nbThread);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++){
                    String urlToFetch = URL + (j+1) + URL_AFTER_PAGE;
                    System.out.println(urlToFetch);

                    Connection connection = Jsoup.connect(urlToFetch);
                    Document doc;
                    try {
                        doc = connection.get();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    transferTest(doc);
                    nbOfPagesCompleted++;
                    percent = (float) Math.round(((float) nbOfPagesCompleted / nbPageMax * 100) * 100) / 100;
                    System.out.println(percent + "% Completed!");
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
        isFetching = false;

        productRepository.saveAll(produits);
        System.out.println("Super C is done.");
        percent = 0;
        nbOfPagesCompleted = 0;
    }

    public static void getIGADatas(){
        isFetching = true;
        int nbPageMax;
        WebDriver getLastPageDriver = new ChromeDriver();
        getLastPageDriver.get(URL + "1" + URL_AFTER_PAGE);
        nbPageMax = Integer.parseInt(getLastPageDriver.findElement(By.cssSelector("a.icon--arrow-skinny-right-double")).getAttribute("href").split("=")[1].split("&")[0]);
        System.out.println(nbPageMax);
        getLastPageDriver.quit();

        int nbThread = 4;


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
        isFetching = false;

        productRepository.saveAll(produits);
        System.out.println("IGA is done." );
    }

    public static void transferTest(Document doc) {
        List<Product> products = new ArrayList<>();

        try (FileWriter writer = new FileWriter("src/main/java/com/example/projetsynthese/resultIGA.html")) {
            writer.write(doc.html());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements productDivs = doc.select("div.item-product__content");
        System.out.println(productDivs.toArray().length);
    }

    public static void transferToArray(WebDriver driver) {
        List<Product> tempList = new ArrayList<>();
        List<WebElement> productTiles = driver.findElements(By.cssSelector("div.item-product__content"));
        for (WebElement productTile : productTiles) {
            String num = productTile.findElement(By.cssSelector("a.js-ga-productname")).getAttribute("href").split("_")[1];
            int i = 0;
            while (num.charAt(i) == '0') {
                i++;
            }
            String id = "iga" + num.substring(i);
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
            String link = "";
            try {
                pricePerHundGram = productTile.findElement(By.cssSelector("div.text--small")).getText();
            } catch (NoSuchElementException e) {
                pricePerHundGram = price + " / " + gram;
            }
            Product product = new Product(id, name, image, brand, price, gram, pricePerHundGram, priceDiscount, isDiscountedThisWeek, false, "IGA", link);
            tempList.add(product);
        }

        synchronized (produits){
            produits.addAll(tempList);
        }
    }
}
