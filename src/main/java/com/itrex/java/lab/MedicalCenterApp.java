package com.itrex.java.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication
public class MedicalCenterApp {

    public static void main(String[] args) {
        SpringApplication.run(MedicalCenterApp.class, args);
    }

}
