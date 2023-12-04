package com.hounter.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HounterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HounterApplication.class, args);
    }

}
