package com.example.demo.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MailSender.class})
@ExtendWith(SpringExtension.class)
class MailSenderTest {
    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private MailSender mailSender;


    @Test
    void testSend() throws MailException {
        doNothing().when(javaMailSender).send((SimpleMailMessage) any());
        mailSender.send("jane.doe@example.org", "Hello from the Dreaming Spires", "Not all who wander are lost");
        verify(javaMailSender).send((SimpleMailMessage) any());
    }
}

