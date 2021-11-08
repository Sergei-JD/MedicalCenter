package com.itrex.java.lab;

import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.config.ApplicationContextConfiguration;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.TimeslotRepository;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.VisitRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MedicalCenterApp {

    public static void main(String[] args) throws RepositoryException {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);

        System.out.println(ctx.getBean(UserRepository.class).getAllUsers());
        System.out.println(ctx.getBean(RoleRepository.class).getAllRoles());
        System.out.println(ctx.getBean(TimeslotRepository.class).getAllTimeslots());
        System.out.println(ctx.getBean(VisitRepository.class).getAllVisits());

    }

}
