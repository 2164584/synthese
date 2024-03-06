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

    private final int NB_MAX_PAGE = 375;

    public SuperC(){
        getDatas();
    }

    private void getDatas() {
        int nbThread = 1;
        int iterationsPerThread = NB_MAX_PAGE / nbThread;
        int remainingIterations = NB_MAX_PAGE / nbThread;
        for (int i = 0; i < nbThread; i++){
            final int start = i * iterationsPerThread;
            final int end = (i == nbThread - 1) ? start + iterationsPerThread + remainingIterations : start + iterationsPerThread;
            Thread thread = new Thread(() -> {
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
            thread.start();
        }
    }

    private void transferToArray(Document doc){
        Elements productDivs = doc.select("div.tile-product");

        for (Element div: productDivs){
            String name = div.select("div.head__title").text();
            String price = div.select("span.price-update").text();//replace("$", "") and replace(",", ".") to remove the dollar sign and the comma

            Product product = new Product(name, price);
            produits.add(product);
        }

        for (Product element: produits){
            System.out.println(element);
        }
        System.out.println(produits.size());
    }
}
