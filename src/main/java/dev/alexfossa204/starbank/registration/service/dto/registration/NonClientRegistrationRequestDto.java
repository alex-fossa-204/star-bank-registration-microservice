package dev.alexfossa204.starbank.registration.service.dto.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.alexfossa204.starbank.registration.controller.constant.RegExConstant;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static dev.alexfossa204.starbank.registration.controller.constant.ValidationErrorMessageConstant.*;

@Data
@Builder
public class NonClientRegistrationRequestDto {

    @Pattern(regexp = RegExConstant.FIRST_NAME_VALIDATION_REGEX, message = FIRST_NAME_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("firstName")
    private String firstName;

    @Pattern(regexp = RegExConstant.MIDDLE_NAME_VALIDATION_REGEX, message = MIDDLE_NAME_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("middleName")
    private String middleName;

    @Pattern(regexp = RegExConstant.LAST_NAME_VALIDATION_REGEX, message = LAST_NAME_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("lastName")
    private String lastName;

    @Pattern(regexp = RegExConstant.PASSPORT_NUMBER_VALIDATION_REGEX, message = PASSPORT_NUMBER_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("passportNumber")
    private String passportNumber;

    @Pattern(regexp = RegExConstant.PHONE_NUMBER_VALIDATION_REGEX, message = PHONE_NUMBER_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Pattern(regexp = RegExConstant.SECRET_QUESTION_VALIDATION_REGEX, message = SECRET_QUESTION_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("secretQuestion")
    private String secretQuestion;

    @Pattern(regexp = RegExConstant.SECRET_QUESTION_ANSWER_VALIDATION_REGEX, message = SECRET_QUESTION_ANSWER_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("secretQuestionAnswer")
    private String secretQuestionAnswer;

    @Pattern(regexp = RegExConstant.PASSWORD_VALIDATION_REGEX, message = PASSWORD_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("newPassword")
    private String newPassword;

    @Pattern(regexp = RegExConstant.PASSWORD_VALIDATION_REGEX, message = PASSWORD_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("confirmPassword")
    private String confirmPassword;

    @Pattern(regexp = RegExConstant.VERIFICATION_CODE_VALIDATION_REGEX, message = VERIFICATION_CODE_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("verificationCode")
    private String verificationCode;

    @Pattern(regexp = RegExConstant.BOOLEAN_FIELD_VALIDATION_REGEX, message = BOOLEAN_VALUE_CHAR_VALIDATION_ERROR_MESSAGE)
    @JsonProperty("isUsResident")
    private String isUsResident;

}
