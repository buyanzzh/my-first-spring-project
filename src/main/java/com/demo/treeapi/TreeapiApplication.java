package com.demo.treeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TreeapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreeapiApplication.class, args);
    }
}
