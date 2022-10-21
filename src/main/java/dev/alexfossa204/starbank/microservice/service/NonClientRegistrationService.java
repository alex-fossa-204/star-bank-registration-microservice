package dev.alexfossa204.starbank.microservice.service;

import dev.alexfossa204.starbank.microservice.service.dto.registration.NonClientRegistrationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.NonClientRegistrationResponseDto;

public interface NonClientRegistrationService {

    NonClientRegistrationResponseDto registerNonClient(NonClientRegistrationRequestDto nonClientData);

    void validateNonClientRegistrationAttempt(NonClientRegistrationRequestDto nonClientData);

}
