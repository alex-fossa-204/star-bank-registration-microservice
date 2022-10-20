package dev.alexfossa204.starbank.registration.service;

import dev.alexfossa204.starbank.registration.service.dto.verification.VerificationCodeGenerationResponseDto;

import java.util.Optional;

public interface VerificationCodeService {

    Optional<VerificationCodeGenerationResponseDto> generateVerificationCode(String phoneNumber);

}
