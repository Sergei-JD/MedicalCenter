package com.itrex.java.lab;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.impl.JDBCUserRepositoryImpl;
import com.itrex.java.lab.repository.impl.RepositoryException;
import com.itrex.java.lab.service.FlywayService;
import org.h2.jdbcx.JdbcConnectionPool;

import java.util.List;

public class MedicalCenterApp {

    private static final String MIGRATIONS_LOCATION = "db/migration";
    private static final String H2_URL = "jdbc:h2:mem:PUBLIC;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    public static void main(String[] args) throws RepositoryException {
        System.out.println("===================START APP======================");
        System.out.println("================START MIGRATION===================");
        FlywayService flywayService = new FlywayService(MIGRATIONS_LOCATION, H2_URL, H2_USER, H2_PASSWORD);
        flywayService.migrate();

        System.out.println("============CREATE CONNECTION POOL================");
        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);

        System.out.println("=============CREATE UserRepository================");
        UserRepository userRepository = new JDBCUserRepositoryImpl(jdbcConnectionPool);
        System.out.println("==================================================\n");

        System.out.println("=============SELECT ALL USERS=====================");
        for (User user : userRepository.getAllUser()) {
            System.out.println(user);
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

        System.out.println("================DELETE USER BY ID = 1================");
        userRepository.deleteUserByID(1);
        for (User user : userRepository.getAllUser()) {
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
        for (User user : userRepository.getAllUser()) {
            System.out.println(user);
        }
        System.out.println("==================================================\n");

//        System.out.println("=============SELECT ALL ROLES=====================");
//        for (Role role : userRepository.getAllRole()) {
//            System.out.println(role);
//        }
//        System.out.println("==================================================\n");

        System.out.println("=========CLOSE ALL UNUSED CONNECTIONS=============");
        jdbcConnectionPool.dispose();
        System.out.println("=================SHUT DOWN APP====================");

    }

}
