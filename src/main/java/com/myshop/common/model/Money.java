package com.myshop.common.model;

/**
 * Created by Mac on 2016. 6. 16..
 */
public class Money {
    private int value;

    public Money(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Money multiply(int multipler) {
        return new Money(value * multipler);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
