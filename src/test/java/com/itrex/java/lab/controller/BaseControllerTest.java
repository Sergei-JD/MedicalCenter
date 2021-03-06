package com.itrex.java.lab.controller;

import com.itrex.java.lab.service.VisitService;
import com.itrex.java.lab.service.DoctorService;
import com.itrex.java.lab.service.PatientService;
import com.itrex.java.lab.service.TimeslotService;
import com.itrex.java.lab.security.jwt.JwtConfigurer;
import com.itrex.java.lab.security.jwt.JwtTokenProvider;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest
public abstract class BaseControllerTest {

    @MockBean
    protected DoctorService doctorService;

    @MockBean
    protected PatientService patientService;

    @MockBean
    protected TimeslotService timeslotService;

    @MockBean
    protected VisitService visitService;

    @MockBean
    protected JwtConfigurer jwtConfigurer;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

}
