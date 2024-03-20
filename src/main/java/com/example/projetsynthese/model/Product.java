package com.example.projetsynthese.model;

public class Product {
    private String name;
    private String image;
    private String brand;
    private String price;
    private String gram;
    private String pricePerHundGram;
    private String discoutPrice;
    private boolean isDiscountedThisWeek;
    private boolean isDiscountedNextWeek;

    public Product(String name, String image, String brand, String price, String gram, String pricePerHundGram, String discoutPrice, boolean isDiscountedThisWeek, boolean isDiscountedNextWeek) {
        this.name = name;
        this.image = image;
        this.brand = brand;
        this.price = price;
        this.gram = gram;
        this.pricePerHundGram = pricePerHundGram;
        this.discoutPrice = discoutPrice;
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
                ", discoutPrice='" + discoutPrice + '\'' +
                ", isDiscountedThisWeek=" + isDiscountedThisWeek +
                ", isDiscountedNextWeek=" + isDiscountedNextWeek +
                '}';
    }
}
