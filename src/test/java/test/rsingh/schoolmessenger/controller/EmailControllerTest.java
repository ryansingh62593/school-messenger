package test.rsingh.schoolmessenger.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import test.rsingh.schoolmessenger.model.Email;
import test.rsingh.schoolmessenger.service.EmailService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@ExtendWith(MockitoExtension.class)
public class EmailControllerTest {
    @Mock
    EmailService emailService;
    EmailController emailController;

    @BeforeEach
    void setUp() {
        emailController = new EmailController(emailService);
    }

    @Test
    void itShouldCheckIfEmailSendingMethodIsInvoked() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Email email = new Email(new Email.EmailHeader(
                "ryansingh62593@gmail.com",
                "ryansingh2387@gmail.com",
                formatter.parse("2022-05-02"),
                "School Messenger"),
                "Hello world!");
        emailController.sendEmail(email);
        verify(emailService).sendEmail(email);
    }

    @Test
    void itShouldCheckIfEmailHeadersRetrievingMethodIsInvoked() {
        emailController.retrieveEmailHeaders();
        verify(emailService).getAllEmailHeaders();
    }
}
