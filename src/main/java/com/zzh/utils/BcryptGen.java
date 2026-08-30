package com.zzh.utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptGen {
    public static void main(String[] args) {
        BCryptPasswordEncoder e = new BCryptPasswordEncoder();
        System.out.println("test   : " + e.encode("123456"));
        System.out.println("admin  : " + e.encode("123456"));
    }
}
