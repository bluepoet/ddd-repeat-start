package com.myshop.member.domain;

/**
 * Created by Mac on 2016. 7. 4..
 */
public class PasswordChangedEvent {
    private String id;
    private String newPassword;

    public String getId() {
        return id;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public PasswordChangedEvent(String id, String newPassword) {
        this.id = id;
        this.newPassword = newPassword;


    }
}
