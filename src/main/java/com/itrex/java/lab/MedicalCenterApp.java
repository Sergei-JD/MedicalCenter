package com.itrex.java.lab;

import org.springframework.context.ApplicationContext;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.config.ApplicationContextConfiguration;
import com.itrex.java.lab.persistence.repository.VisitRepository;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MedicalCenterApp {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);

        System.out.println(ctx.getBean(UserRepository.class).getAllUsers());
        System.out.println(ctx.getBean(RoleRepository.class).getAllRoles());
        System.out.println(ctx.getBean(TimeslotRepository.class).getAllTimeslots());
        System.out.println(ctx.getBean(VisitRepository.class).getAllVisits());

    }

}
