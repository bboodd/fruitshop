package com.shop.fruitshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class FruitshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(FruitshopApplication.class, args);
    }

}
