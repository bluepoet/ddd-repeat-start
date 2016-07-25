package com.myshop.springsecurity;

import com.myshop.springconfig.SecurityConfig;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by bluepoet on 2016. 7. 26..
 */
public class AuthCookieHelper {
    static Cookie authCookie(String id) {
        try {
            return new Cookie(SecurityConfig.AUTHCOOKIENAME, URLEncoder.encode(id, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
