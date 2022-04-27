package com.gover.plague.cache;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class PlagueCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlagueCacheApplication.class, args);
    }

}
