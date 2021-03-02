package com.tyymt.wxplatform.service.portal.user.api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sun.misc.BASE64Decoder;

import java.io.IOException;

public class TestMain {

    public static void main(String[] args) {
        final BASE64Decoder decoder = new BASE64Decoder();
        try {
            System.out.println(new String(decoder.decodeBuffer("YWRtaW4="), "UTF-8"));

            System.out.println(new BCryptPasswordEncoder().encode("admin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
