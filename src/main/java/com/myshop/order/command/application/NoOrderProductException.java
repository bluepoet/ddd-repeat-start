package com.myshop.order.command.application;

/**
 * Created by Mac on 2016. 6. 21..
 */
public class NoOrderProductException extends RuntimeException {
    private String productId;

    public NoOrderProductException(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
