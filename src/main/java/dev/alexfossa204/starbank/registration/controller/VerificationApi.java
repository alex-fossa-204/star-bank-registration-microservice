package dev.alexfossa204.starbank.registration.controller;

import dev.alexfossa204.starbank.registration.config.swagger.annotation.SwaggerOperationVerificationCodeGeneration;
import dev.alexfossa204.starbank.registration.service.dto.verification.VerificationCodeGenerationResponseDto;
import dev.alexfossa204.starbank.registration.service.dto.verification.VerificationCodeGenerationRequestDto;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Tag(name = "Verification Code management API", description = "This Component is responsible for creating an event on Apache Kafka cluster, which will be handled by Listener Microservice")
@RequestMapping(value = "/star-bank/registration-management")
public interface VerificationApi extends ErrorHandlerApi {

    @SwaggerOperationVerificationCodeGeneration
    @RequestMapping(value = "user/verification-code/new-code", method = RequestMethod.POST)
    ResponseEntity<VerificationCodeGenerationResponseDto> sendRequestForVerificationCodeGeneration(@RequestBody
                                                                                                   @Parameter(name = "User Phone Number", required = true)
                                                                                                   @Valid VerificationCodeGenerationRequestDto verificationCodeGenerationRequestDto);

}
