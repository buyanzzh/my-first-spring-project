package com.zzh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.zzh.mapper")
public class TreeapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreeapiApplication.class, args);
    }
}
