package com.itrex.java.lab;

import com.itrex.java.lab.repository.*;
import com.itrex.java.lab.repository.hibernateimpl.HibernateRoleRepositoryImpl;
import com.itrex.java.lab.repository.hibernateimpl.HibernateTimeslotRepositoryImpl;
import com.itrex.java.lab.repository.hibernateimpl.HibernateUserRepositoryImpl;
import com.itrex.java.lab.repository.hibernateimpl.HibernateVisitRepositoryImpl;
import com.itrex.java.lab.service.FlywayService;
import com.itrex.java.lab.util.HibernateUtil;

public class MedicalCenterApp {

    public static void main(String[] args) throws RepositoryException {
        System.out.println("===================START APP======================");
        System.out.println("================START MIGRATION===================");
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();

        System.out.println("=============CREATE UserRepository================");
        UserRepository userRepository = new HibernateUserRepositoryImpl(HibernateUtil.getSessionFactory().openSession());
        RoleRepository roleRepository = new HibernateRoleRepositoryImpl(HibernateUtil.getSessionFactory().openSession());
        TimeslotRepository timeslotRepository = new HibernateTimeslotRepositoryImpl(HibernateUtil.getSessionFactory().openSession());
        VisitRepository visitRepository = new HibernateVisitRepositoryImpl(HibernateUtil.getSessionFactory().openSession());
        System.out.println("==================================================\n");

        userRepository.getAllUsers().forEach(System.out::println);

        System.out.println("=========CLOSE ALL UNUSED SESSION=============");
        HibernateUtil.getSessionFactory().close();
        System.out.println("=================SHUT DOWN APP====================");
    }

}
