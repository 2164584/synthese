package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SuperC {
    public static boolean isDoneFetching = false;
    public static boolean isFecthing = false;
    public static final String URL = "https://www.superc.ca/recherche";
    private static List<Product> produits = new ArrayList<>();

    public SuperC(){
        if (!isDoneFetching && !isFecthing){
            getSupercDatas();
        }
    }

    public List<Product> getProduits() {
        return produits;
    }

    private static void getSupercDatas() {
        isFecthing = true;
        int nbPageMax;

        Connection connectionForPages = Jsoup.connect(URL);
        try {
            Document document = connectionForPages.get();
            Elements lastPage = document.select("a.ppn--element");
            nbPageMax = Integer.parseInt(lastPage.get(lastPage.size() - 2).text());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(nbPageMax);

        int nbThread = 8;

        Thread[] threads = new Thread[nbThread];
        for (int i = 0; i < nbThread; i++){
            final int start = i * (nbPageMax / nbThread);
            final int end = (i == nbThread - 1) ? nbPageMax : start + (nbPageMax / nbThread);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++){
                    String urlToFetch = URL +"-page-"+j;

                    Connection connection = Jsoup.connect(urlToFetch);
                    Document doc;
                    try {
                        doc = connection.get();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    transferToArray(doc);
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
        isFecthing = false;
    }

    public static void transferToArray(Document doc) {
        Elements productDivs = doc.select("div.tile-product");

        List<Product> tempList = new ArrayList<>();

        for (Element div : productDivs) {
            String name = div.select("div.head__title").text();
            String priceDiscount = div.select("div.promo-price").text();
            boolean isDiscountedThisWeek = !div.select("div.promo-price").text().isEmpty();
            String price = "";
            if (isDiscountedThisWeek) {
                price = div.select("div.pricing__before-price").text();
            }
            else {
                price = div.select("span.price-update").text();
            }
            String gram = div.select("span.head__unit-details").text();
            String pricePerHundGram = div.select("div.pricing__secondary-price > span:first-child").text();
            String image = div.select("img").get(1).attr("src");

            String brand = div.select("span.head__brand").text();
            Product product = new Product("superc1", name, image, brand, price, gram, pricePerHundGram, priceDiscount, isDiscountedThisWeek, false, "Super C");
            tempList.add(product);
        }

        synchronized (produits) {
            produits.addAll(tempList);
        }
        System.out.println("Super C: " + produits.size());
    }
}
