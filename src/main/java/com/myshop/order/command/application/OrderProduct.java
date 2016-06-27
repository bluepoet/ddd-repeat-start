package com.myshop.order.command.application;

/**
 * Created by Mac on 2016. 6. 21..
 */
public class OrderProduct {
    private String productId;
    private int quantity;

    public OrderProduct() {
    }

    public OrderProduct(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
