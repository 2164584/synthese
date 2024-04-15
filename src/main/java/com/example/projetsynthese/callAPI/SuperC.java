package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.repository.SuperCRepository;
import lombok.Getter;
import org.apache.catalina.core.ApplicationContext;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class SuperC {
    private final SuperCRepository superCRepository;

    public boolean isDoneFetching = false;
    public boolean isFecthing = false;
    public final String URL = "https://www.superc.ca/recherche";

    private List<Product> produits = new ArrayList<>();

    @Autowired
    public SuperC(SuperCRepository superCRepository) {
        this.superCRepository = superCRepository;
        getSupercDatas();
    }



    private void getSupercDatas() {

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
            //threads[i] = new Thread(() -> {
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
                    System.out.println("Page " + j + " of " + nbThread + " done.");
                }
            //});
            //threads[i].start();
        }


        /*
        for (int i = 0; i < nbThread; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         */

        isDoneFetching = true;
        isFecthing = false;
    }

    public void transferToArray(Document doc) {
        Elements productDivs = doc.select("div.tile-product");

        for (Element div : productDivs) {
            String id = "sc"+ div.attr("data-product-code");
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
            Product product = new Product(id, name, image, brand, price, gram, pricePerHundGram, priceDiscount, isDiscountedThisWeek, false, "Super C");
            superCRepository.save(product);
        }

        /*
        synchronized (superCRepository) {
            System.out.println("In transferToArray 1");
            superCRepository.saveAllAndFlush(produits);
        }
         */

    }
}
