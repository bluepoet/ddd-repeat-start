package com.myshop.integration;

/**
 * Created by Mac on 2016. 6. 27..
 */
public interface OffsetStore {
    long get();
    void update(long nextOffSet);
}
