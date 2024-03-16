package com.damyo.alpha.controller;

import com.damyo.alpha.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUser() {

    }

    @Test
    void updateName() {
    }

    @Test
    void updateProfile() {
    }

    @Test
    void updateScore() {
    }

    @Test
    void deleteUser() {
    }
}