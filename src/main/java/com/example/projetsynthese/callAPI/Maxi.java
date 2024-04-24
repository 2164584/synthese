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
import java.util.Arrays;
import java.util.List;

@Component
public class Maxi {
    private static ProductRepository productRepository;

    public static boolean isFetching = false;
    public static final String URL = "https://www.maxi.ca/alimentation/c/27985?page=";

    @Getter
    private static List<Product> produits = new ArrayList<>();

    @Autowired
    public Maxi(ProductRepository productRepository){
        Maxi.productRepository = productRepository;
    }

    public static void getMaxiDatas(){
        isFetching = true;
        int nbPageMax;

        WebDriver getLastPageDriver = new ChromeDriver();
        getLastPageDriver.get(URL + "1");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> navElement = getLastPageDriver.findElements(By.cssSelector("button.css-1f4yp4r"));
        nbPageMax = Integer.parseInt(navElement.get(navElement.size() - 1).getText());
        getLastPageDriver.quit();
        System.out.println(nbPageMax);

        for (int i = 1; i <= nbPageMax; i++){
            System.out.println("Maxi is fetching page " + i + "...");
            String urlToFetch = URL + i;
            WebDriver driver = new ChromeDriver();
            driver.get(urlToFetch);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            transferToArray(driver);
            driver.quit();
        }

        isFetching = false;

        productRepository.saveAll(produits);
        System.out.println("Maxi is done.");

    }

    public static void transferToArray(WebDriver driver){
        List<WebElement> elements = driver.findElements(By.cssSelector("div.css-vhnn8v"));
        for (WebElement element : elements){
            String price = "";
            String discountPrice = "";
            boolean isDiscountThisWeek = false;
            String brand = "";
            String gram = "";
            String pricePerHundGram = "";

            try {
                price = element.findElement(By.cssSelector("span.css-pwnbcb")).findElement(By.cssSelector("span")).getText();
            } catch (NoSuchElementException e){
                try {
                    price = element.findElement(By.cssSelector("span.css-623q5h")).findElement(By.cssSelector("span")).getText();
                } catch (NoSuchElementException e1){
                    price = "";
                }
                try {
                    discountPrice = element.findElement(By.cssSelector("span.css-o93gbd")).findElement(By.cssSelector("span")).getText();
                    isDiscountThisWeek = true;
                } catch (NoSuchElementException e1){
                    discountPrice = "";
                }
            }

            try {

                brand = element.findElement(By.cssSelector("p.css-1ecdp9w")).getText();
            } catch (NoSuchElementException e){
                brand = "";
            }

            String og = element.findElement(By.cssSelector("p.css-1yftjin")).getText();
            int index = og.indexOf(",");

            if (index > -1 && Character.isDigit(og.charAt(index - 1))) {
                gram = "Unité variable";
                pricePerHundGram = og;
            } else {
                gram = og.substring(0, index);
                pricePerHundGram = og.substring(index + 1).trim();
            }

            String id = "";
            try {
                id = "maxi" + element.findElement(By.cssSelector("h3.css-6qrhwc")).getAttribute("id").split("_")[0];
            } catch (NoSuchElementException e){
                id = "N/A";
            }

            String name = "";
            try {
                name = element.findElement(By.cssSelector("h3.css-6qrhwc")).getText();
            } catch (NoSuchElementException e){
                name = "N/A";
            }
            String image = "";
            try {
                image = element.findElement(By.cssSelector("img.css-oguq8l")).getAttribute("src");
            } catch (NoSuchElementException e){
                image = "N/A";
            }

            Product product = new Product(id, name, image, brand, price, gram, pricePerHundGram, discountPrice, isDiscountThisWeek, false, "Maxi");
            produits.add(product);
        }
    }
}
