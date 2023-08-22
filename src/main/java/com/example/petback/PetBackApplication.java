package com.example.petback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PetBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetBackApplication.class, args);
    }

}
