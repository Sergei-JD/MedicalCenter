package com.itrex.java.lab.controller.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import com.itrex.java.lab.controller.BaseControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

class AuthControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void login_validData_shouldExecuteLogIn() throws Exception {
        //given
        String loginPath = "/login";
        String adminEmail = "ivan@email.com";
        String adminPassword = "password";

        //when && then
        mockMvc.perform(formLogin(loginPath).user(adminEmail).password(adminPassword)).andExpect(authenticated());
    }

    @Test
    void logout_validData_shouldExecuteLogOut() throws Exception {
        //given
        String logoutPath = "/logout";

        //when & then
        mockMvc.perform(logout(logoutPath)).andExpect(unauthenticated());
    }

}
