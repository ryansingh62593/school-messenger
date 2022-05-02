package test.rsingh.schoolmessenger.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import test.rsingh.schoolmessenger.model.Email;
import test.rsingh.schoolmessenger.service.EmailService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmailController.class)
public class EmailControllerIntTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmailService emailService;

    @Test
    void itShouldRetrieveDefaultEmailHeader() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        when(emailService.getAllEmailHeaders())
                .thenReturn(List.of(new Email.EmailHeader(
                        "ryansingh62593@gmail.com",
                        "ryansingh2387@gmail.com",
                        formatter.parse("2022-05-02"),
                        "School Messenger")));
        mockMvc.perform(get("/api/v1/sm/emails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].from", is("ryansingh62593@gmail.com")))
                .andExpect(jsonPath("$[0].to", is("ryansingh2387@gmail.com")))
                .andExpect(jsonPath("$[0].date", is("2022-05-02")))
                .andExpect(jsonPath("$[0].subject", is("School Messenger")));
    }

    @Test
    void itShouldCallEmailSendingMethod() throws Exception {
        doNothing().when(emailService).sendEmail(isA(Email.class));

        String email = "{" +
                "\"header\": {" +
                "\"from\": \"ryansingh62593@gmail.com\"," +
                "\"to\": \"ryansingh2387@gmail.com\"," +
                "\"date\": \"2022-05-02\"," +
                "\"subject\": \"School Messenger\"" +
                "}," +
                "\"body\" : \"Hello World!\"" +
                "}";

        mockMvc.perform(post("/api/v1/sm/emails").content(email).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Successfully sent email from ryansingh62593@gmail.com to ryansingh2387@gmail.com")));
    }

    @Test
    void itShouldReturnBadRequestError() throws Exception {
        doNothing().when(emailService).sendEmail(isA(Email.class));

        String email = "{" +
                "\"header\": {" +
                "\"from\": \"ryansingh62593gmail.com\"," +
                "\"to\": \"ryansingh2387gmail.com\"," +
                "\"date\": \"2022-05-02\"" +
                "}" +
                "}";

        mockMvc.perform(post("/api/v1/sm/emails").content(email).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['header.from']", is("must be a well-formed email address")))
                .andExpect(jsonPath("$.['header.to']", is("must be a well-formed email address")))
                .andExpect(jsonPath("$.['header.subject']", is("Subject is mandatory")))
                .andExpect(jsonPath("$.body", is("Body is mandatory")));
    }
}
