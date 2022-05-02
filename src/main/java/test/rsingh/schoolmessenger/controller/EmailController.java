package test.rsingh.schoolmessenger.controller;

import org.springframework.web.bind.annotation.*;
import test.rsingh.schoolmessenger.model.Email;
import test.rsingh.schoolmessenger.service.EmailService;

import javax.validation.Valid;
import java.util.List;

import static test.rsingh.schoolmessenger.config.Constants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH + "/emails")
public class EmailController {
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * An API handler for sending emails using email service.
     * @method POST
     * @URI /api/v1/sm/emails
     * @param email: receive an email payload
     * @return a message
     */
    @PostMapping()
    public String sendEmail(@Valid @RequestBody Email email) {
        emailService.sendEmail(email);
        String response = String.format("Successfully sent email from %s to %s", email.getHeader().getFrom(), email.getHeader().getTo());
        return response;
    }

    /**
     * An API handler for retrieving all the email headers
     * @method GET
     * @URI /api/v1/sm/emails
     * @return a list of email headers
     */
    @GetMapping()
    public List<Email.EmailHeader> retrieveEmailHeaders() {
        List<Email.EmailHeader> headers = emailService.getAllEmailHeaders();
        return headers;
    }
}
