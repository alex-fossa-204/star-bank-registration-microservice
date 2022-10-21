package dev.alexfossa204.starbank.microservice.service;

import dev.alexfossa204.starbank.microservice.service.dto.verification.VerificationCodeGenerationResponseDto;

import java.util.Optional;

public interface VerificationCodeService {

    Optional<VerificationCodeGenerationResponseDto> generateVerificationCode(String phoneNumber);

}
