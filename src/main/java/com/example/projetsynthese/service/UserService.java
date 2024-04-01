package com.example.projetsynthese.service;

import com.example.projetsynthese.callAPI.IGA;
import com.example.projetsynthese.callAPI.Metro;
import com.example.projetsynthese.callAPI.SuperC;
import com.example.projetsynthese.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private SuperC superC = new SuperC();
    private Metro metro = new Metro();
    private IGA iga = new IGA();

    public UserService() {
    }

    public List<Product> getIGADatas(){
        int nbPageMax;
        WebDriver getLastPageDriver = new ChromeDriver();
        getLastPageDriver.get(iga.URL + "1" + iga.URL_AFTER_PAGE);
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
                    String urlToFetch = iga.URL + (j+1) + iga.URL_AFTER_PAGE;
                    WebDriver driver = new ChromeDriver();
                    driver.get(urlToFetch);
                    iga.transferToArray(driver);
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

        iga.isDoneFetching = true;
        return iga.getProduits();
    }

    public List<Product> getMetroDatas() {
        int nbPageMax;

        WebDriver getLastPageDriver = new ChromeDriver();
        getLastPageDriver.get(metro.URL);
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

    public List<Product> getSuperCProduct(){
        return superC.getProduits();
    }

}
