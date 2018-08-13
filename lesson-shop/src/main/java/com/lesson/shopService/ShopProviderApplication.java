package com.lesson.shopService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = "classpath:shopService-provider.xml")
public class ShopProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopProviderApplication.class, args);
        System.out.println("ShopService is started");
    }
}
