package com.example.solidconnection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SolidConnectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolidConnectionApplication.class, args);
    }

}
