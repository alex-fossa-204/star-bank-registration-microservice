package dev.alexfossa204.starbank.microservice.service.dto.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static dev.alexfossa204.starbank.microservice.controller.constant.RegExConstant.*;
import static dev.alexfossa204.starbank.microservice.controller.constant.ValidationErrorMessageConstant.*;

@Data
@Builder
public class VerificationCodeCheckRequestDto {

    @Pattern(regexp = VERIFICATION_CODE_VALIDATION_REGEX, message = VERIFICATION_CODE_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("verificationCode")
    private String verificationCode;

    @Pattern(regexp = PHONE_NUMBER_VALIDATION_REGEX, message = PHONE_NUMBER_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("phoneNumber")
    private String phoneNumber;

}
