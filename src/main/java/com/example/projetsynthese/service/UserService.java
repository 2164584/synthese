package com.example.projetsynthese.service;

import com.example.projetsynthese.callAPI.Metro;
import com.example.projetsynthese.callAPI.SuperC;
import com.example.projetsynthese.model.Product;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    private SuperC superC = new SuperC();
    private Metro metro = new Metro();

    public UserService() {
    }

    public List<Product> getMetroDatas() {
        int nbPageMax;

        WebDriver getLastPageDriver = new ChromeDriver();
        getLastPageDriver.get(metro.URL);
        List<WebElement> lastPage = getLastPageDriver.findElements(By.cssSelector("a.ppn--element"));
        nbPageMax = Integer.parseInt(lastPage.get(lastPage.size() - 2).getText());
        getLastPageDriver.quit();

        int nbThread = 4;

        Thread[] threads = new Thread[nbThread];
        for (int i = 0; i < nbThread; i++){
            final int start = i * (nbPageMax / nbThread);
            final int end = (i == nbThread - 1) ? nbPageMax : start + (nbPageMax / nbThread);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++){
                    String urlToFetch = metro.URL + "-page-"+j;
                    WebDriver driver = new ChromeDriver();
                    driver.get(urlToFetch);
                    metro.transferToArray(driver);
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
        metro.isDoneFetching = true;
        return metro.getProduits();
    }

    public List<Product> getSupercDatas() {
        int nbPageMax;

        Connection connectionForPages = Jsoup.connect(superC.URL);
        try {
            Document document = connectionForPages.get();
            Elements lastPage = document.select("a.ppn--element");
            nbPageMax = Integer.parseInt(lastPage.get(lastPage.size() - 2).text());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int nbThread = 8;

        Thread[] threads = new Thread[nbThread];
        for (int i = 0; i < nbThread; i++){
            final int start = i * (nbPageMax / nbThread);
            final int end = (i == nbThread - 1) ? nbPageMax : start + (nbPageMax / nbThread);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++){
                    String urlToFetch = superC.URL +"-page-"+j;

                    Connection connection = Jsoup.connect(urlToFetch);
                    Document doc;
                    try {
                        doc = connection.get();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    superC.transferToArray(doc);
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

        superC.isDoneFetching = true;
        return superC.getProduits();
    }

}
