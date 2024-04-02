package com.example.projetsynthese.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    @JsonProperty("name")
    private String name;
    @JsonProperty("image")
    private String image;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("price")
    private String price;
    @JsonProperty("gram")
    private String gram;
    @JsonProperty("pricePerHundGram")
    private String pricePerHundGram;
    @JsonProperty("discountPrice")
    private String discountPrice;
    @JsonProperty("isDiscountedThisWeek")
    private boolean isDiscountedThisWeek;
    @JsonProperty("isDiscountedNextWeek")
    private boolean isDiscountedNextWeek;

    public Product(String name, String image, String brand, String price, String gram, String pricePerHundGram, String discountPrice, boolean isDiscountedThisWeek, boolean isDiscountedNextWeek) {
        this.name = name;
        this.image = image;
        this.brand = brand;
        this.price = price;
        this.gram = gram;
        this.pricePerHundGram = pricePerHundGram;
        this.discountPrice = discountPrice;
        this.isDiscountedThisWeek = isDiscountedThisWeek;
        this.isDiscountedNextWeek = isDiscountedNextWeek;
    }

    public Product(String name, String price){
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                ", gram=" + gram +
                ", pricePerHundGram='" + pricePerHundGram + '\'' +
                ", discoutPrice='" + discountPrice + '\'' +
                ", isDiscountedThisWeek=" + isDiscountedThisWeek +
                ", isDiscountedNextWeek=" + isDiscountedNextWeek +
                '}';
    }
}
