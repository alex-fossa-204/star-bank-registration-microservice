package dev.alexfossa204.starbank.registration.config.swagger.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "[Registration 4-th step] A-Bank Client registration process (Web App)",
        method = "clientRegistration",
        requestBody = @RequestBody(
                description = "__[Login request body]:__\n\n" +
                        "__(phoneNumber)__ - represents __(unique)__ user phone number value into the database.\n\n" +
                        "__(newPassword)__ - represents user password value into the database.\n\n" +
                        "__(confirmPassword)__ - represents user password confirm value value.\n\n" +
                        "__(secretQuestion)__ - represents user secret question value into the database.\n\n" +
                        "__(secretQuestionAnswer)__ - represents user secret question answer value into the database.\n\n" +
                        "__(confirmPassword)__ - represents user unique verification code value.\n\n" +
                        "__(verificationCode)__ - represents generated verification code, which is required for password recovery and registration.\n\n"),
        description = "Setting up credentials for user, which was an A-Bank client. [Client means, that database contains all necessary information about user, like passport data. User must enter password and secret phrase]")
public @interface SwaggerOperationClientRegistration {
}
