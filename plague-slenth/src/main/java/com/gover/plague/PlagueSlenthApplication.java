package com.gover.plague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 本模块需要启动 zipkin.jar
 */
@SpringBootApplication
public class PlagueSlenthApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlagueSlenthApplication.class, args);
    }

}
