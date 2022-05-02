package test.rsingh.schoolmessenger.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import test.rsingh.schoolmessenger.model.Email;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static test.rsingh.schoolmessenger.config.Constants.EMAIL_HISTORY;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    EmailService emailService;
    @Mock
    JavaMailSender mailSender;

    @BeforeEach
    void setUp() {
        emailService = new EmailService(mailSender);
    }

    @AfterEach
    void tearDown() throws Exception {
        EMAIL_HISTORY.clear();
    }

    @Test
    void itShouldCheckIfEmailIsSent() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Email email = new Email(new Email.EmailHeader(
                    "ryansingh62593@gmail.com",
                    "ryansingh2387@gmail.com",
                    formatter.parse("2022-05-02"),
                    "School Messenger"),
                    "Hello world!");
        // When
        emailService.sendEmail(email);
        // Then
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void itShouldCheckIfEmailIsLogged() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Email email = new Email(new Email.EmailHeader(
                "ryansingh62593@gmail.com",
                "ryansingh2387@gmail.com",
                formatter.parse("2022-05-02"),
                "School Messenger"),
                "Hello world!");
        // When
        emailService.sendEmail(email);
        // Then
        assertEquals(true, EMAIL_HISTORY.contains(email));
    }

    @Test
    void itShouldCheckIfAllEmailHeadersAreRetrieved() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Email email = new Email(new Email.EmailHeader(
                "ryansingh62593@gmail.com",
                "ryansingh2387@gmail.com",
                formatter.parse("2022-05-02"),
                "School Messenger"),
                "Hello world!");
        emailService.sendEmail(email);
        // When
        List<Email.EmailHeader> headers = emailService.getAllEmailHeaders();
        // Then
        assertEquals(true, headers.contains(email.getHeader()));
        assertEquals(1, headers.size());
    }
}