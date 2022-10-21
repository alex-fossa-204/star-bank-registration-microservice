package dev.alexfossa204.starbank.microservice.config.swagger.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "[Registration 1-st step] A-Bank Phone Number verification process (Web App)",
        method = "verifyClientCredentialForExists",
        requestBody = @RequestBody(
                description = "__[Phone number verification request body]:__\n\n" +
                        "__(phoneNumber)__ - represents user phone number into the database.\n\n"),
        description = "Sending request to the server and checking user phone number credential for exists")
public @interface SwaggerOperationVerifyClientPhoneNumberForExist {
}
