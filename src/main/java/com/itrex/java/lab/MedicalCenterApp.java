package com.itrex.java.lab;

import com.itrex.java.lab.config.ApplicationContextConfiguration;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.repository.hibernateimpl.HibernateRoleRepositoryImpl;
import com.itrex.java.lab.repository.hibernateimpl.HibernateTimeslotRepositoryImpl;
import com.itrex.java.lab.repository.hibernateimpl.HibernateUserRepositoryImpl;
import com.itrex.java.lab.repository.hibernateimpl.HibernateVisitRepositoryImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MedicalCenterApp {

    public static void main(String[] args) throws RepositoryException {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);

        System.out.println(ctx.getBean(HibernateUserRepositoryImpl.class).getAllUsers());
        System.out.println(ctx.getBean(HibernateRoleRepositoryImpl.class).getAllRoles());
        System.out.println(ctx.getBean(HibernateTimeslotRepositoryImpl.class).getAllTimeslots());
        System.out.println(ctx.getBean(HibernateVisitRepositoryImpl.class).getAllVisits());

    }

}
