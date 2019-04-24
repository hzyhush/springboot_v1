package com.unicom.api.cterminal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.unicom.api.cterminal.dao")//扫描dao层
//@EnableCaching//开启缓存
public class CTerminalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CTerminalApplication.class, args);
    }

}

