package com.myshop.springsecurity

import com.myshop.SpringIntTestConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

/**
 * Created by bluepoet on 2016. 7. 24..
 */
@SpringIntTestConfig
class AuthenticationTest extends Specification {
    @Autowired
    ProviderManager authenticationManager

    def "유효한 아이디와 비밀번호로 인증에 성공한다."() {
        when:
        def auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("user1", "1234"))
        then:
        auth.isAuthenticated() == true
    }

    def "유효하지 않은 아이디와 비밀번호로 인증에 실패한다."() {
        when:
        def auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("user1", "badpw"))
        then:
        thrown BadCredentialsException
    }


}