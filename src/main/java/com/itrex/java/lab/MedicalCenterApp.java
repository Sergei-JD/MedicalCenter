package com.itrex.java.lab;

import com.itrex.java.lab.service.DoctorService;
import com.itrex.java.lab.service.impl.DoctorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.config.ApplicationContextConfiguration;
import com.itrex.java.lab.persistence.repository.VisitRepository;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Slf4j
public class MedicalCenterApp {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);

        log.info("GetAllUsers: {}", ctx.getBean(UserRepository.class).getAllUsers());
        log.info("GetAllRoles: {}", ctx.getBean(RoleRepository.class).getAllRoles());
        log.info("GetAllTimeslots: {}", ctx.getBean(TimeslotRepository.class).getAllTimeslots());
        log.info("GetAllVisits: {}", ctx.getBean(VisitRepository.class).getAllVisits());

        DoctorService doctorService = ctx.getBean(DoctorService.class);
        doctorService.getAllDoctors();

        Arrays.stream(ctx.getBean(Environment.class).getActiveProfiles()).forEach(log::info);

        log.info("Informational conclusion! Everything is fine!");
        log.error("Error! Something went wrong!");
        Logger.getRootLogger().setLevel(Level.INFO);

    }

}
