package com.gree.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gree")
public class HospApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospApplication.class,args);
    }
}
