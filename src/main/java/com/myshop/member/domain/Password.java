package com.myshop.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Mac on 2016. 6. 21..
 */
@Embeddable
public class Password {
    @Column(name = "password")
    private String value;

    protected Password() {
    }

    public Password(String value) {
        this.value = value;
    }

    public boolean match(String password) {
        return this.value.equals(password);
    }
}

