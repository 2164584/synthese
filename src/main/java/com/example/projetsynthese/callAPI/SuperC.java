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
    public final String URL = "https://www.superc.ca/recherche";
    private List<Product> produits = new ArrayList<>();

    private int nbPageMax;

    public SuperC(){
        getDatas();
    }

    private void getDatas() {

        Connection connectionForPages = Jsoup.connect(URL);
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
                    String urlToFetch = URL;
                    if(j != 0){
                        urlToFetch += "-page-"+j;
                    }

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
        System.out.println("fecthing done");
    }

    private void transferToArray(Document doc) {
        Elements productDivs = doc.select("div.tile-product");

        List<Product> tempList = new ArrayList<>();

        for (Element div : productDivs) {
            String name = div.select("div.head__title").text();
            String price = div.select("span.price-update").text();

            Product product = new Product(name, price);
            tempList.add(product);
        }

        synchronized (produits) {
            produits.addAll(tempList);
        }
        System.out.println(produits.size());
    }
}
