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
        int nbThread = 8;
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

        //convert my list to Set to remove duplicates
        List<Product> products = new ArrayList<>(produits);
        produits.clear();
        produits.addAll(products);

    }

    private void transferToArray(Document doc){
        Elements productDivs = doc.select("div.tile-product");

        List<Product> tempList = new ArrayList<>();

        for (Element div: productDivs){
            String name = div.select("div.head__title").text();
            String price = div.select("span.price-update").text();

            Product product = new Product(name, price);
            tempList.add(product);
        }

        synchronized (produits){
            produits.addAll(tempList);
        }
    }
}
