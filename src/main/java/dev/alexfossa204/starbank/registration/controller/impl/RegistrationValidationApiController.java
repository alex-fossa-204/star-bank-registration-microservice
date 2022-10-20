package dev.alexfossa204.starbank.registration.controller.impl;

import dev.alexfossa204.starbank.registration.controller.RegistrationValidationApi;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationResponseDto;
import dev.alexfossa204.starbank.registration.service.ClientVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationValidationApiController implements RegistrationValidationApi {

    private final ClientVerificationService clientVerificationService;

    @Override
    public ResponseEntity<ClientVerificationResponseDto> verifyClientPhoneNumberCredentialForExists(ClientVerificationRequestDto clientVerificationRequestDto) {
        ClientVerificationResponseDto clientRegistrationResponseDto = clientVerificationService.verifyClientForExists(clientVerificationRequestDto);
        return new ResponseEntity<>(clientRegistrationResponseDto, clientRegistrationResponseDto.getHttpStatus());
    }
}
