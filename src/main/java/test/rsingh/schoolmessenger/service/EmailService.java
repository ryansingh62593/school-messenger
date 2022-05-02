package test.rsingh.schoolmessenger.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import test.rsingh.schoolmessenger.model.Email;

import java.util.List;
import java.util.stream.Collectors;

import static test.rsingh.schoolmessenger.config.Constants.EMAIL_HISTORY;

/**
 * A Service that provides support for emails
 */
@Service
public class EmailService {
    JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * A method to send email with the email information.
     * If email is successfully sent, it will be logged on email history.
     * @param email
     */
    public void sendEmail(Email email) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getHeader().getFrom());
            message.setTo(email.getHeader().getTo());
            message.setSubject(email.getHeader().getSubject());
            message.setSentDate(email.getHeader().getDate());
            message.setText(email.getBody());

            mailSender.send(message);
            EMAIL_HISTORY.add(email);
    }

    /**
     * A method that returns all the records of email headers in email history.
     * @return a list of email header
     */
    public List<Email.EmailHeader> getAllEmailHeaders() {
        return EMAIL_HISTORY.stream()
                            .map(Email::getHeader)
                            .collect(Collectors.toList());
    }
}
