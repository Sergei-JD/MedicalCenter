package com.itrex.java.lab;

import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.entity.Visit;
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

//        userRepository.getAllUsers().forEach(System.out::println); //ok
//        userRepository.getAllUserByRole("Doctor").forEach(System.out::println); //ok
//        roleRepository.getAllRole().forEach(System.out::println);  //ok
//        timeslotRepository.getAllTimeslot().forEach(System.out::println);  //ok
        visitRepository.getAllVisit().forEach(System.out::println);  //  ok

//        System.out.println(userRepository.getUserById(1)); //ok
//        System.out.println(userRepository.getUserByEmail("petr@email.com")); //ok
//        System.out.println(roleRepository.getRoleByName("Doctor")); //ok
//        System.out.println(timeslotRepository.getTimeslotByID(1)); //ok
//        System.out.println(visitRepository.getVisitById(1)); //ok

//         User addUser = new User();
//         addUser.setFirstName("John");
//         addUser.setLastName("Obama");
//         addUser.setAge(55);
//         addUser.setEmail("obama@gmail.com");
//         addUser.setPassword("58tjgf4");
//         addUser.setGender("M");
//         addUser.setPhoneNum(333333333);
//         userRepository.add(addUser);
//         for (User user : userRepository.getAllUsers()) {
//         System.out.println(user);
//         }                                                //  ok

//        Role addRole = new Role();
//        addRole.setName("Admin");
//        roleRepository.add(addRole);
//        for (Role role : roleRepository.getAllRole()) {
//            System.out.println(role);
//        }                                                 //  ok

//        Timeslot addTimeslot = new Timeslot();
//        addTimeslot.setTimeslotId(4);
//        addTimeslot.setStartTime(Time.valueOf("12:12"));
//        addTimeslot.setDate(Date.valueOf("2021-10-10"));
//        addTimeslot.setOffice(505);
//        timeslotRepository.add(addTimeslot);
//        for (Role role : roleRepository.getAllRole()) {
//            System.out.println(role);
//        }                                                 //  no

//        Visit addVisit = new Visit();
//        addVisit.setDoctor(userRepository.getUserById(5));
//        addVisit.setPatient(userRepository.getUserById(6));
//        addVisit.setTimeslot(timeslotRepository.getTimeslotByID(3));
//        addVisit.setComment("ok");
//        for (Visit visit : visitRepository.getAllVisit()) {
//            System.out.println(visit);
//        }                                                   //  ok

//         userRepository.deleteUserById(1);
//         for (User user : userRepository.getAllUsers()) {
//         System.out.println(user);
//         }                                                  //  ok

//         timeslotRepository.deleteTimeslotById(1);
//         for (Timeslot timeslot : timeslotRepository.getAllTimeslot()) {
//         System.out.println(timeslot);
//         }                                                  //  ok

//         visitRepository.deleteVisitById(1);
//         for (Visit visit : visitRepository.getAllVisit()) {
//         System.out.println(visit);
//         }                                                  //  ok

        System.out.println("=========CLOSE ALL UNUSED SESSION=============");
        HibernateUtil.getSessionFactory().close();
        System.out.println("=================SHUT DOWN APP====================");
    }

}
