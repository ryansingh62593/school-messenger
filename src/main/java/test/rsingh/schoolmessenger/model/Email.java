package test.rsingh.schoolmessenger.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    @Valid
    EmailHeader header;
    @NotBlank(message = "Body is mandatory")
    String body;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailHeader {
        @javax.validation.constraints.Email
        @NotBlank(message = "From email is mandatory")
        String from;
        @javax.validation.constraints.Email
        @NotBlank(message = "To email is mandatory")
        String to;
        @NotNull(message = "Date is mandatory")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date date;
        @NotBlank(message = "Subject is mandatory")
        String subject;
    }
}
