package dev.alexfossa204.starbank.registration.service;

import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationResponseDto;

public interface ClientVerificationService {

    ClientVerificationResponseDto verifyClientForExists(ClientVerificationRequestDto clientVerificationRequestDto);

}
