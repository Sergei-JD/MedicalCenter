package com.itrex.java.lab.controller;

import com.itrex.java.lab.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

}
