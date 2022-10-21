package dev.alexfossa204.starbank.microservice.service.dto.registration;

import dev.alexfossa204.starbank.microservice.service.dto.verification.ClientDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"timeStamp"})
public class NonClientRegistrationResponseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Minsk")
    @JsonProperty("timeStamp")
    private Date timeStamp;

    @JsonProperty("httpStatus")
    private HttpStatus httpStatus;

    @JsonProperty("message")
    private String message;

    @JsonIgnore
    private ClientDto clientDto;

}
