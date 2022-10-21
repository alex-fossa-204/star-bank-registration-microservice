package dev.alexfossa204.starbank.microservice.service;

import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientVerificationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientVerificationResponseDto;

public interface ClientVerificationService {

    ClientVerificationResponseDto verifyClientForExists(ClientVerificationRequestDto clientVerificationRequestDto);

}
