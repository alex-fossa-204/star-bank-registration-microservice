package dev.alexfossa204.starbank.registration.controller.impl;

import dev.alexfossa204.starbank.registration.controller.VerificationApi;
import dev.alexfossa204.starbank.registration.service.dto.verification.VerificationCodeGenerationResponseDto;
import dev.alexfossa204.starbank.registration.service.dto.verification.VerificationCodeGenerationRequestDto;
import dev.alexfossa204.starbank.registration.service.VerificationCodeService;
import dev.alexfossa204.starbank.registration.service.exception.VerificationCodeGeneratorCrashedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import static dev.alexfossa204.starbank.registration.service.constant.ServiceExceptionConstant.*;


@RestController
@Validated
@RequiredArgsConstructor
public class VerificationApiController implements VerificationApi {

    private final VerificationCodeService verificationCodeService;

    @Override
    public ResponseEntity<VerificationCodeGenerationResponseDto> sendRequestForVerificationCodeGeneration(VerificationCodeGenerationRequestDto verificationCodeGenerationRequestDto) {
        Optional<VerificationCodeGenerationResponseDto> verificationCodeGenerationResponseDto = verificationCodeService.generateVerificationCode(verificationCodeGenerationRequestDto.getPhoneNumber());
        if (verificationCodeGenerationResponseDto.isEmpty()) {
            throw new VerificationCodeGeneratorCrashedException(STATUS_CODE_BAD_REQUEST_VERIFICATION_CODE_CAN_NOT_BE_GENERATED);
        }
        return new ResponseEntity<>(verificationCodeGenerationResponseDto.get(), verificationCodeGenerationResponseDto.get().getHttpStatus());
    }

}
