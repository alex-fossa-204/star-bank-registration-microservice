package dev.alexfossa204.starbank.registration.service;

import dev.alexfossa204.starbank.registration.service.dto.registration.NonClientRegistrationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.NonClientRegistrationResponseDto;

public interface NonClientRegistrationService {

    NonClientRegistrationResponseDto registerNonClient(NonClientRegistrationRequestDto nonClientData);

    void validateNonClientRegistrationAttempt(NonClientRegistrationRequestDto nonClientData);

}
