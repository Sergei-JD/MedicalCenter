package com.itrex.java.lab;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.config.ApplicationContextConfiguration;
import com.itrex.java.lab.persistence.repository.VisitRepository;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class MedicalCenterApp {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);

        log.info("GetAllUsers: {}", ctx.getBean(UserRepository.class).getAllUsers());
        log.info("GetAllRoles: {}", ctx.getBean(RoleRepository.class).getAllRoles());
        log.info("GetAllTimeslots: {}", ctx.getBean(TimeslotRepository.class).getAllTimeslots());
        log.info("GetAllVisits: {}", ctx.getBean(VisitRepository.class).getAllVisits());

    }

}
