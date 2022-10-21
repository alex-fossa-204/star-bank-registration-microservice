package dev.alexfossa204.starbank.microservice.controller;

import dev.alexfossa204.starbank.microservice.config.swagger.annotation.SwaggerOperationClientRegistration;
import dev.alexfossa204.starbank.microservice.config.swagger.annotation.SwaggerOperationNonClientRegistration;
import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientRegistrationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientRegistrationResponseDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.NonClientRegistrationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.NonClientRegistrationResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Tag(name = "Registration management API", description = "This Component is responsible for creating records in database, corresponding user data as registered unit")
@RequestMapping(value = {"/star-bank/registration-management", "/"})
public interface RegistrationApi extends ErrorHandlerApi {

    @SwaggerOperationNonClientRegistration
    @RequestMapping(value = "/registration/non-client", method = RequestMethod.POST)
    ResponseEntity<NonClientRegistrationResponseDto> nonClientRegistration(@RequestBody @Valid NonClientRegistrationRequestDto userRegistrationDto);

    @SwaggerOperationClientRegistration
    @RequestMapping(value = "/registration/client", method = RequestMethod.POST)
    ResponseEntity<ClientRegistrationResponseDto> clientRegistration(@RequestBody @Valid ClientRegistrationRequestDto userRegistrationDto);

}
