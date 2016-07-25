package com.myshop.springsecurity

import com.myshop.SecurityContextUtil
import com.myshop.SpringIntTestConfig
import com.myshop.member.application.BlockMemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification


/**
 * Created by bluepoet on 2016. 7. 25..
 */
@SpringIntTestConfig
class MethodAccessTest extends Specification {
    @Autowired
    BlockMemberService blockMemberService

    void cleanup() {
        SecurityContextHolder.clearContext()
    }

    def "관리자가 아니면 유저를 차단시킬 수 없다."() {
        given:
        SecurityContextUtil.setAuthentication("user2", "ROLE_USER")

        when:
        blockMemberService.block("user1")

        then:
        thrown AccessDeniedException
    }

    def "관리자면 유저를 차단할 수 있다."() {
        given:
        SecurityContextUtil.setAuthentication("admin", "ROLE_ADMIN")

        when:
        blockMemberService.block("user1")

        then:
        notThrown AccessDeniedException
    }
}