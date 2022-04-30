package com.gover.plague;

import com.gover.plague.channel.OrderLogChannel;
import com.gover.plague.config.BindingConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@Import(value = { BindingConfig.class })
public class PlagueMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlagueMqApplication.class, args);
    }

}
