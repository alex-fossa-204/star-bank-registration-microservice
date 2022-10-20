package dev.alexfossa204.starbank.registration.controller;

import dev.alexfossa204.starbank.registration.config.swagger.annotation.SwaggerOperationVerifyClientPhoneNumberForExist;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Tag(name = "Registration validation management API", description = "This Component is responsible for user credential validation")
@RequestMapping(value = {"/star-bank/registration-management/", "/"})
public interface RegistrationValidationApi extends ErrorHandlerApi {

    @SwaggerOperationVerifyClientPhoneNumberForExist
    @RequestMapping(value = "/verification/credentials/phone-number", method = RequestMethod.POST)
    ResponseEntity<ClientVerificationResponseDto> verifyClientPhoneNumberCredentialForExists(@RequestBody @Valid ClientVerificationRequestDto clientVerificationRequestDto);

}
