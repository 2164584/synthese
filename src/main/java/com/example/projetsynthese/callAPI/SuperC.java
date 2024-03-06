package com.example.projetsynthese.callAPI;

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
    private List<String> produits = new ArrayList<>();

    private final int NB_MAX_PAGE = 375;

    public SuperC(){
        getDatas();
    }

    private void getDatas() {
        try {
            Connection connection;
            Document doc;
            String urlToFetch;
            for (int i = 0; i < NB_MAX_PAGE; i++){
                urlToFetch = URL;
                if(i != 0){
                    urlToFetch += "-page-"+i;
                }
                connection = Jsoup.connect(urlToFetch);
                doc = connection.get();
                transferToArray(doc);
            }
        }
        catch (IOException ignored){

        }
    }

    private void transferToArray(Document doc){
        Elements productDivs = doc.select("div.tile-product");

        for (Element div: productDivs){
            String name = div.select("div.head__title").text();
            String price = div.select("span.price-update").text();

            String infos = "Name: " + name + ", Price: " + price;
            produits.add(infos);
        }

        for (String element: produits){
            System.out.println(element);
        }
        System.out.println(produits.size());
    }
}
