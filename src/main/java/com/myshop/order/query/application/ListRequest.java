package com.myshop.order.query.application;

/**
 * Created by Mac on 2016. 6. 26..
 */
public class ListRequest {
    private int page;
    private int size;

    public ListRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
