package dev.alexfossa204.starbank.registration.config.swagger.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "[Registration 4-th step] A-Bank Non-Client registration process (Web App)",
        method = "nonClientRegistration",
        requestBody = @RequestBody(
                description = "__[Login request body]:__\n\n" +
                        "__(firstName)__ - represents user first name value into the database.\n\n" +
                        "__(middleName)__ - represents user middle name value into the database.\n\n" +
                        "__(lastName)__ - represents user last name value into the database.\n\n" +
                        "__(passportNumber)__ - represents __(unique)__ user passport number value into the database.\n\n" +
                        "__(phoneNumber)__ - represents __(unique)__ user phone number value into the database.\n\n" +
                        "__(secretQuestion)__ - represents user secret question value into the database.\n\n" +
                        "__(secretQuestionAnswer)__ - represents user secret question answer value into the database.\n\n" +
                        "__(newPassword)__ - represents user password value into the database.\n\n" +
                        "__(confirmPassword)__ - represents user password confirm value value.\n\n" +
                        "__(confirmPassword)__ - represents user unique verification code value.\n\n" +
                        "__(verificationCode)__ - represents generated verification code, which is required for password recovery and registration.\n\n" +
                        "__(isUkResident)__ - represents user resident state value.\n\n"),
        description = "Creating record in database for non-client of A-Bank. Setting up credentials for new user. [Non-Client means, that there is no any information in database about current user. User must enter passport data, password and secret phrase]")
public @interface SwaggerOperationNonClientRegistration {
}
