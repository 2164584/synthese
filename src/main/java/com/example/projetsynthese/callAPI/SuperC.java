package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

public class SuperC {
    private Long id;

    public boolean isDoneFetching = false;
    public final String URL = "https://www.superc.ca/recherche";
    private List<Product> produits = new ArrayList<>();

    public SuperC(){}

    public List<Product> getProduits() {
        return produits;
    }

    public void transferToArray(Document doc) {
        Elements productDivs = doc.select("div.tile-product");

        List<Product> tempList = new ArrayList<>();

        for (Element div : productDivs) {
            String name = div.select("div.head__title").text();
            String price = div.select("span.price-update").text();
            String gram = div.select("span.head__unit-details").text();
            String pricePerHundGram = div.select("div.pricing__secondary-price > span:first-child").text();
            String image = div.select("img").get(1).attr("src");
            String priceDiscount = div.select("div.promo-price").text();
            boolean isDiscountedThisWeek = !div.select("div.promo-price").text().isEmpty();
            String brand = div.select("span.head__brand").text();
            Product product = new Product(name, image, brand, price, gram, pricePerHundGram, priceDiscount, isDiscountedThisWeek, false);
            tempList.add(product);
        }

        synchronized (produits) {
            produits.addAll(tempList);
        }
        System.out.println(produits.size());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
