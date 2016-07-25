package com.myshop.springsecurity

import com.myshop.SpringIntTestConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.FilterChainProxy
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by bluepoet on 2016. 7. 25..
 */
@SpringIntTestConfig
@WebAppConfiguration
class WebAccessTest extends Specification {
    @Autowired
    FilterChainProxy filterChainProxy

    @Autowired
    WebApplicationContext context

    MockMvc mockMvc

    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(filterChainProxy).build()
    }

    def "어떤 유저든지 홈페이지는 들어올 수 있다."() {
        expect:
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
    }

    def "인증되지 않은 유저는 로그인페이지로 리다이렉트시킨다."() {
        expect:
        mockMvc.perform(get("/my/orders"))
                .andDo(print())
                .andExpect(redirectedUrlPattern("**/login"))
    }

    def "인증된 유저는 개인페이지에 접근할 수 있다."() {
        expect:
        mockMvc.perform(get("/my/main").cookie(AuthCookieHelper.authCookie("user1")))
                .andExpect(status().isOk())
    }

    def "관리자가 아닌 유저는 관리자페이지에 접근할 수 없다."() {
        expect:
        mockMvc.perform(get("/admin").cookie(AuthCookieHelper.authCookie("user1")))
                .andExpect(status().isForbidden())
    }
}