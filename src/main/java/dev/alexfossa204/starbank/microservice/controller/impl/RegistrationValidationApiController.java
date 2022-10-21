package dev.alexfossa204.starbank.microservice.controller.impl;

import dev.alexfossa204.starbank.microservice.controller.RegistrationValidationApi;
import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientVerificationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientVerificationResponseDto;
import dev.alexfossa204.starbank.microservice.service.ClientVerificationService;
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
