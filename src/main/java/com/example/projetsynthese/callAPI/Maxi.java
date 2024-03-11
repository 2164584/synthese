package com.example.projetsynthese.callAPI;

import com.example.projetsynthese.model.Product;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class Maxi {
    public final String URL = "https://www.metro.ca/epicerie-en-ligne/recherche?sortOrder=relevance&filter=%3Arelevance";
    private List<Product> produits = new ArrayList<>();

    private final int NB_MAX_PAGE = 375;

    public Maxi(){
        getDatas();
    }

    private void getDatas() {

        Document doc = Jsoup.connect(URL)
                .header(":authority", "www.metro.ca")
                .header(":method", "GET")
                .header(":path", "/epicerie-en-ligne/recherche?sortOrder=relevance&filter=%3Arelevance")
                .header(":scheme", "https")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Encoding", "gzip, deflate, br, zstd")
                .header("Accept-Language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Cache-Control", "max-age=0")
                .header("Referer", "https://www.metro.ca/epicerie-en-ligne/recherche?sortOrder=relevance&filter=%3Arelevance")
                .header("Sec-Ch-Ua", "\"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122\"")
                .header("Sec-Ch-Ua-Mobile", "?0")
                .header("Sec-Ch-Ua-Platform", "\"Windows\"")
                .header("Sec-Fetch-Dest", "document")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Sec-Fetch-Site", "same-origin")
                .header("Sec-Fetch-User", "?1")
                .header("Upgrade-Insecure-Requests", "1")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                .cookie("METRO_ANONYMOUS_COOKIE", "021467a3-c1bc-4d3a-b9bc-8f59e638d558")
                .cookie("show-store-banner", "true")
                .get();

        try {
            doc = connection.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(doc);
    }
}
