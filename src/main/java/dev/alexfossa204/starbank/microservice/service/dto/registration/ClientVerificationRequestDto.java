package dev.alexfossa204.starbank.microservice.service.dto.registration;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

import static dev.alexfossa204.starbank.microservice.controller.constant.RegExConstant.*;
import static dev.alexfossa204.starbank.microservice.controller.constant.ValidationErrorMessageConstant.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientVerificationRequestDto {

    @Pattern(regexp = PHONE_NUMBER_VALIDATION_REGEX, message = PHONE_NUMBER_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("phoneNumber")
    private String phoneNumber;
}
