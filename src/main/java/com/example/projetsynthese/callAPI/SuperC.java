package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import com.example.projetsynthese.repository.ProductRepository;
import lombok.Getter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class SuperC {
    private static ProductRepository productRepository;

    public static boolean isFetching = false;
    public static final String URL = "https://www.superc.ca/recherche";

    private static int nbOfPagesCompleted = 0;
    @Getter
    private static float percent = 0;
    @Getter
    private static List<Product> produits = new ArrayList<>();

    @Autowired
    public SuperC(ProductRepository productRepository) {
        SuperC.productRepository = productRepository;
    }

    public static float getPercent(){
        return percent;
    }

    public static void getSupercDatas() {
        produits.clear();
        isFetching = true;
        int nbPageMax;

        Connection connectionForPages = Jsoup.connect(URL);
        try {
            Document document = connectionForPages.get();
            try (FileWriter writer = new FileWriter("src/main/java/com/example/projetsynthese/resultSuperC.html")) {
                writer.write(document.html());
            }
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

    public static void transferToArray(Document doc) {
        List<Product> products = new ArrayList<>();

        Elements productDivs = doc.select("div.tile-product");

        for (Element div : productDivs) {
            String num = div.attr("data-product-code");
            int i = 0;
            while (num.charAt(i) == '0') {
                i++;
            }
            String id = "sc"+ num.substring(i);
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
            String image = div.select("picture.defaultable-picture").select("img").attr("src");
            if(!image.startsWith("https")){
                image = "https://www.superc.ca" + image;
            }
            String link = "https://www.superc.ca" + div.select("a.product-details-link").attr("href");
            String category = div.attr("data-product-category");

            String brand = div.select("span.head__brand").text();
            Product product = new Product(id, name, image, brand, price, gram, pricePerHundGram, priceDiscount, isDiscountedThisWeek, false, "SuperC" ,link);
            products.add(product);
        }

        synchronized (produits) {
            produits.addAll(products);
        }
    }
}
