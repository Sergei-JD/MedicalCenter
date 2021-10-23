package com.itrex.java.lab;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.TimeslotRepository;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.VisitRepository;
import com.itrex.java.lab.repository.impl.*;
import com.itrex.java.lab.service.FlywayService;
import org.h2.jdbcx.JdbcConnectionPool;

import static com.itrex.java.lab.properties.Properties.*;

public class MedicalCenterApp {

    public static void main(String[] args) throws RepositoryException {
        System.out.println("===================START APP======================");
        System.out.println("================START MIGRATION===================");
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();

        System.out.println("============CREATE CONNECTION POOL================");
        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);

        System.out.println("=============CREATE UserRepository================");
        UserRepository userRepository = new JDBCUserRepositoryImpl(jdbcConnectionPool);
        RoleRepository roleRepository = new JDBCRoleRepositoryImpl(jdbcConnectionPool);
        TimeslotRepository timeslotRepository = new JDBCTimeslotRepositoryImpl(jdbcConnectionPool);
        VisitRepository visitRepository = new JDBCVisitRepositoryImpl(jdbcConnectionPool);
        System.out.println("==================================================\n");

        System.out.println("=============SELECT ALL USERS=====================");
        for (User user : userRepository.getAllUsers()) {
            System.out.println(user);
        }
        System.out.println("==================================================\n");

        System.out.println("=============SELECT ALL ROLES=====================");
        for (Role role : roleRepository.getAllRole()) {
            System.out.println(role);
        }
        System.out.println("==================================================\n");

        System.out.println("=============SELECT ALL TIMESLOT=====================");
        for (Timeslot timeslot : timeslotRepository.getAllTimeslot()) {
            System.out.println(timeslot);
        }
        System.out.println("==================================================\n");

        System.out.println("=============SELECT ALL VISITS=====================");
        for (Visit visit : visitRepository.getAllVisit()) {
            System.out.println(visit);
        }
        System.out.println("==================================================\n");

        System.out.println("=============SELECT USER BY ID = 3==================");
        User selectedUserById = userRepository.getUserById(3);
        System.out.println(selectedUserById);
        System.out.println("==================================================\n");

        System.out.println("======SELECT USER BY EMAIL = \"petr@email.com\"======");
        User selectedUserByEmail = userRepository.getUserByEmail("petr@email.com");
        System.out.println(selectedUserByEmail);
        System.out.println("==================================================\n");

        System.out.println("======SELECT ROLE BY NAME = Doctor======");
        Role selectedRoleByName = roleRepository.getRoleByName("Doctor");
        System.out.println(selectedRoleByName);
        System.out.println("==================================================\n");

        System.out.println("=============SELECT TIMESLOT BY ID = 1==================");
        Timeslot selectedTimeslotById = timeslotRepository.getTimeslotByID(1);
        System.out.println(selectedTimeslotById);
        System.out.println("==================================================\n");

        System.out.println("=============SELECT VISIT BY ID = 1==================");
        Visit selectedVisitById = visitRepository.getVisitById(1);
        System.out.println(selectedVisitById);
        System.out.println("==================================================\n");

        System.out.println("================DELETE USER BY ID = 1================");
        userRepository.deleteUserById(1);
        for (User user : userRepository.getAllUsers()) {
            System.out.println(user);
        }
        System.out.println("==================================================\n");

        System.out.println("============SELECT ALL DOCTOR USERS================");
        for (User userRole : userRepository.getAllUserByRole("Doctor")) {
            System.out.println(userRole);
        }
        System.out.println("==================================================\n");

        System.out.println("===================ADD USER=========================");
        User addUser = new User();
        addUser.setFirstName("John");
        addUser.setLastName("Obama");
        addUser.setAge(55);
        addUser.setEmail("obama@gmail.com");
        addUser.setPassword("58tjgf4");
        addUser.setGender("M");
        addUser.setPhoneNum(333333333);
        userRepository.add(addUser);
        for (User user : userRepository.getAllUsers()) {
            System.out.println(user);
        }
        System.out.println("==================================================\n");

        System.out.println("=========CLOSE ALL UNUSED CONNECTIONS=============");
        jdbcConnectionPool.dispose();
        System.out.println("=================SHUT DOWN APP====================");
    }

}
