package dev.alexfossa204.starbank.microservice.service.dto.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ClientDto implements Serializable {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phoneLogin")
    private String phoneLogin;

    @JsonProperty("passportLogin")
    private String passportLogin;

}
