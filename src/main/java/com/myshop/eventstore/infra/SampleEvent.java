package com.myshop.eventstore.infra;

/**
 * Created by bluepoet on 2016. 7. 29..
 */
public class SampleEvent {
    private String name;
    private int value;

    public SampleEvent() {
    }

    public SampleEvent(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
