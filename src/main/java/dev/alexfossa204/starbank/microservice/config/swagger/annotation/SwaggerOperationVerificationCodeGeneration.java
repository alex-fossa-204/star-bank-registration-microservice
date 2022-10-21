package dev.alexfossa204.starbank.microservice.config.swagger.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "[Registration 2-nd step] Generate 6 digit verification code",
        method = "sendRequestForVerificationCodeGeneration",
        requestBody = @RequestBody(
                description = "__[Verification code generation request body]:__\n\n" +
                        "__(phoneNumber)__ - represents user phone number into the database.\n\n"),
        description = "Generates an event on Kafka Cluster. Event reason: code generation. Verification Code Microserivce listens for this event")
public @interface SwaggerOperationVerificationCodeGeneration {
}
