package com.myshop.member.domain;

/**
 * Created by Mac on 2016. 6. 21..
 */
public interface MemberRepository {
    Member findById(MemberId memberId);
}
