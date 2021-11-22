package com.itrex.java.lab;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import com.itrex.java.lab.service.DoctorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.persistence.repository.VisitRepository;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;

@SpringBootApplication
public class MedicalCenterApp implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(MedicalCenterApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Environment env = context.getBean(Environment.class);
        DoctorService doctorService = context.getBean(DoctorService.class);

        doctorService.getAllDoctors();

        for (String activeProfile : env.getActiveProfiles()) {
            System.out.println(activeProfile);
        }

    }

}
