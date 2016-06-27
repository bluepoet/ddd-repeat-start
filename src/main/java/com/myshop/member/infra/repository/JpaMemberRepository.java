package com.myshop.member.infra.repository;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Mac on 2016. 6. 21..
 */
@Repository
public class JpaMemberRepository implements MemberRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Member findById(MemberId memberId) {
        return entityManager.find(Member.class, memberId);
    }
}
