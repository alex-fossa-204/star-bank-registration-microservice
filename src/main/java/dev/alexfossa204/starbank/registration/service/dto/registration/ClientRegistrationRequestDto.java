package dev.alexfossa204.starbank.registration.service.dto.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static dev.alexfossa204.starbank.registration.controller.constant.RegExConstant.*;
import static dev.alexfossa204.starbank.registration.controller.constant.ValidationErrorMessageConstant.*;

@Data
@Builder
public class ClientRegistrationRequestDto {

    @Pattern(regexp = PHONE_NUMBER_VALIDATION_REGEX, message = PHONE_NUMBER_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Pattern(regexp = PASSWORD_VALIDATION_REGEX, message = PASSWORD_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("newPassword")
    private String newPassword;

    @Pattern(regexp = PASSWORD_VALIDATION_REGEX, message = PASSWORD_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("confirmPassword")
    private String confirmPassword;

    @Pattern(regexp = SECRET_QUESTION_VALIDATION_REGEX, message = SECRET_QUESTION_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("secretQuestion")
    private String secretQuestion;

    @Pattern(regexp = SECRET_QUESTION_ANSWER_VALIDATION_REGEX, message = SECRET_QUESTION_ANSWER_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("secretQuestionAnswer")
    private String secretQuestionAnswer;

    @Pattern(regexp = VERIFICATION_CODE_VALIDATION_REGEX, message = VERIFICATION_CODE_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("verificationCode")
    private String verificationCode;

}
