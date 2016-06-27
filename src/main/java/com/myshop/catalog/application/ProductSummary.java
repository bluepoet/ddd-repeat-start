package com.myshop.catalog.application;

/**
 * Created by Mac on 2016. 6. 20..
 */
public class ProductSummary {
    private String id;
    private String name;
    private int price;
    private String image;

    public ProductSummary(String id, String name, int price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
