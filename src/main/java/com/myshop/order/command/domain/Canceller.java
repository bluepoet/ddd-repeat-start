package com.myshop.order.command.domain;

/**
 * Created by Mac on 2016. 6. 24..
 */
public class Canceller {
    private String memberId;

    public Canceller(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}
