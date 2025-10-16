package com.training.demo.tribble;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.training.demo.tribble")
@EntityScan("com.training.demo.tribble.domain")
@EnableJpaRepositories("com.training.demo.tribble.repository")
public class MobileShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(MobileShopApplication.class, args);
    }
}
