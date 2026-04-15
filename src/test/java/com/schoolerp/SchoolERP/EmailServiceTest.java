package com.schoolerp.SchoolERP;

import com.schoolerp.SchoolERP.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail() {
        emailService.sendEmail("princemaurya90600@gmail.com","Java mail sender test","did you get mail or not");
    }
}
