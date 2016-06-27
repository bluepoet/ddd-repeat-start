package com.myshop.eventstore.api;

/**
 * Created by Mac on 2016. 6. 27..
 */
public class PayloadConvertException extends RuntimeException {
    public PayloadConvertException(Exception e) {
        super(e);
    }
}