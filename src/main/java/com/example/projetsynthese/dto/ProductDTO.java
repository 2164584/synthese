package com.example.projetsynthese.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String image;
    private String brand;
    private String price;
    private String gram;
    private String discountPrice;
    private boolean isDiscountedThisWeek;

    public String toString() {
        return "ProductDTO{" +
                "id=" + id + '\''+
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                ", gram='" + gram + '\'' +
                ", discountPrice='" + discountPrice + '\'' +
                ", isDiscountedThisWeek='" + isDiscountedThisWeek + '\'' +
                '}';
    }
}
