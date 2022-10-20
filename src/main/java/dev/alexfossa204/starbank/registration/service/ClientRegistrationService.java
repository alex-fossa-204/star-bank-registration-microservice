package dev.alexfossa204.starbank.registration.service;

import dev.alexfossa204.starbank.registration.service.dto.registration.ClientRegistrationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientRegistrationResponseDto;

public interface ClientRegistrationService {

    ClientRegistrationResponseDto registerClient(ClientRegistrationRequestDto clientData);

    void validateClientRegistrationAttempt(ClientRegistrationRequestDto clientData);

}
