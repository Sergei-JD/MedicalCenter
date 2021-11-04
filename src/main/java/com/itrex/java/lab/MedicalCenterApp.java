package com.itrex.java.lab;

import com.itrex.java.lab.config.ApplicationContextConfiguration;
import com.itrex.java.lab.exception_handler.RepositoryException;
import com.itrex.java.lab.repository.*;
import com.itrex.java.lab.repository.hibernateimpl.HibernateRoleRepositoryImpl;
import com.itrex.java.lab.repository.hibernateimpl.HibernateTimeslotRepositoryImpl;
import com.itrex.java.lab.repository.hibernateimpl.HibernateUserRepositoryImpl;
import com.itrex.java.lab.repository.hibernateimpl.HibernateVisitRepositoryImpl;
import com.itrex.java.lab.service.FlywayService;
import com.itrex.java.lab.util.HibernateUtil;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.itrex.java.lab.properties.Properties.*;

public class MedicalCenterApp {

    public static void main(String[] args) throws RepositoryException {
        System.out.println("===================START APP======================");
        System.out.println("================START MIGRATION===================");
        FlywayService flywayService = new FlywayService(MIGRATIONS_LOCATION);
        flywayService.migrate();

        System.out.println("============CREATE CONNECTION POOL================");

        System.out.println("=============CREATE UserRepository================");

        System.out.println("==================================================\n");

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);

        System.out.println(ctx.getBean(HibernateUserRepositoryImpl.class));
        UserRepository userRepository = ctx.getBean(UserRepository.class);
        userRepository.getAllUsers().stream().forEach(System.out::println);

        System.out.println(ctx.getBean(HibernateRoleRepositoryImpl.class));
        RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
        roleRepository.getAllRoles().stream().forEach(System.out::println);

        System.out.println(ctx.getBean(HibernateTimeslotRepositoryImpl.class));
        TimeslotRepository timeslotRepository = ctx.getBean(TimeslotRepository.class);
        timeslotRepository.getAllTimeslots().stream().forEach(System.out::println);

        System.out.println(ctx.getBean(HibernateVisitRepositoryImpl.class));
        VisitRepository visitRepository = ctx.getBean(VisitRepository.class);
        visitRepository.getAllVisits().stream().forEach(System.out::println);

        System.out.println("=========CLOSE ALL UNUSED SESSION=============");
        System.out.println("=================SHUT DOWN APP====================");
    }

}
