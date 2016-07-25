package com.myshop.member.domain;

/**
 * Created by bluepoet on 2016. 7. 24..
 */
public class MemberBlockedEvent {
    private final String memberId;

    public MemberBlockedEvent(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}
