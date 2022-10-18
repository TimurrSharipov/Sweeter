package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepo;

import java.util.Collections;
import java.util.HashSet;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private MailSender mailSender;

    @MockBean
    private UserRepo userRepo;


    @Autowired
    private UserService userService;

    @Test
    public void isUserCreated(){
        User user = new User();
        user.setEmail("some@mail");
        boolean isUserCrete = userService.addUser(user);
        assertTrue(isUserCrete);
        assertNotNull(user.getActivationCode());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        verify(userRepo, times(1)).save(user);
        verify(mailSender, times(1)).send(
                ArgumentMatchers.eq(user.getEmail()),
                ArgumentMatchers.eq("Activation code"),
                ArgumentMatchers.any()
        );
    }
    @Test
    public void addUserFailTest() {
        User user = new User();

        user.setUsername("John");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("John");

        boolean isUserCreated = userService.addUser(user);

        Assert.assertFalse(isUserCreated);

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailSender, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void activateUser() {
        User user = new User();

        user.setActivationCode("bingo!");

        Mockito.doReturn(user)
                .when(userRepo)
                .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("activate");

        Assert.assertTrue(isUserActivated);
        Assert.assertNull(user.getActivationCode());

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }


    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        User user = new User();
        user.setActivationCode("Activation Code");
        user.setActive(true);
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setPassword("pass");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        when(userRepo.findByUsername(any())).thenReturn(user);
        assertSame(user, userService.loadUserByUsername("janedoe"));
        verify(userRepo).findByUsername(user.getUsername());
    }


    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        when(userRepo.findByUsername(any()))
                .thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("janedoe"));
        verify(userRepo).findByUsername(any());
    }

    @Test
    void testAddUser() {
        doNothing().when(mailSender).send(any(), any(), any());

        User user = new User();
        user.setActivationCode("Activation Code");
        user.setActive(true);
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setPassword("pass");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setActivationCode("Activation Code");
        user1.setActive(true);
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setPassword("pass");
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");
        when(userRepo.findByUsername(any())).thenReturn(user);
        when(userRepo.save(any())).thenReturn(user1);

        User user2 = new User();
        user2.setActivationCode("Activation Code");
        user2.setActive(true);
        user2.setEmail("jane.doe@example.org");
        user2.setId(123L);
        user2.setPassword("pass");
        user2.setRoles(new HashSet<>());
        user2.setUsername("janedoe");
        assertFalse(userService.addUser(user2));
        verify(userRepo).findByUsername(user2.getUsername());
    }

    @Test
    void testAddUser2() {
        doNothing().when(mailSender).send(any(), any(), any());
        when(userRepo.findByUsername(any())).thenThrow(new UsernameNotFoundException(
                "Hello, %s! \nWelcome to Sweater. Please, visit next link: http://localhost:%s/activate/%s"));
        when(userRepo.save(any())).thenThrow(new UsernameNotFoundException(
                "Hello, %s! \nWelcome to Sweater. Please, visit next link: http://localhost:%s/activate/%s"));

        User user = new User();
        user.setActivationCode("Activation Code");
        user.setActive(true);
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setPassword("pass");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        assertThrows(UsernameNotFoundException.class, () -> userService.addUser(user));
        verify(userRepo).findByUsername(any());
    }

}

