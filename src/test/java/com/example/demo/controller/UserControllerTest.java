package com.example.demo.controller;

import static org.mockito.Mockito.when;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepo;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserRepo userRepo;

    @Test
    void testUserEditForm() throws Exception {
        User user = new User();
        user.setActivationCode("Activation Code");
        user.setActive(true);
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setPassword("password");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{user}", user);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    @Test
    void testUserEditForm2() throws Exception {
        when(userRepo.findAll()).thenReturn(new ArrayList<>());

        User user = new User();
        user.setActivationCode("Activation Code");
        user.setActive(true);
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setPassword("pass");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{user}", "", "Uri Variables");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.view().name("userList"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userList"));
    }


    @Test
    void testUserList() throws Exception {
        when(userRepo.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.view().name("userList"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userList"));
    }

    @Test
    void testUserList2() throws Exception {
        when(userRepo.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.view().name("userList"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userList"));
    }

    @Test
    void testUserSave() throws Exception {
        when(userRepo.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user").param("username", "foo");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.view().name("userList"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userList"));
    }
}

