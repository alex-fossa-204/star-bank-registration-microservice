package dev.alexfossa204.starbank.registration.controller.impl;

import dev.alexfossa204.starbank.registration.controller.RegistrationApi;
import dev.alexfossa204.starbank.registration.controller.rest.VerificationCodeValidationRestTemplate;
import dev.alexfossa204.starbank.registration.service.dto.broker.VerificationCodeSetAsUsedTopicMessage;
import dev.alexfossa204.starbank.registration.service.ClientRegistrationService;
import dev.alexfossa204.starbank.registration.service.NonClientRegistrationService;
import dev.alexfossa204.starbank.registration.service.impl.broker.KafkaTopicProducerService;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientRegistrationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientRegistrationResponseDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.NonClientRegistrationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.NonClientRegistrationResponseDto;
import dev.alexfossa204.starbank.registration.service.constant.KafkaServiceConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class RegistrationApiController implements RegistrationApi {

    private final NonClientRegistrationService nonClientRegistrationService;

    private final ClientRegistrationService clientRegistrationService;

    private final KafkaTopicProducerService kafkaTopicProducerService;

    private final VerificationCodeValidationRestTemplate verificationCodeValidationRestTemplate;


    @Override
    public ResponseEntity<NonClientRegistrationResponseDto> nonClientRegistration(NonClientRegistrationRequestDto userRegistrationDto) {
        nonClientRegistrationService.validateNonClientRegistrationAttempt(userRegistrationDto);
        verificationCodeValidationRestTemplate.sendVerificationCodeValidationPostRequest(userRegistrationDto.getPhoneNumber(), userRegistrationDto.getVerificationCode()); //create sender bean
        kafkaTopicProducerService.publishVerificationCodeSetAsUsedTopicEvent(VerificationCodeSetAsUsedTopicMessage.builder()
                .publicationTimeStamp(new Date())
                .publicationReason(KafkaServiceConstant.VERIFICATION_CODE_GENERATION_REASON_NON_CLIENT_REGISTRATION)
                .phoneNumber(userRegistrationDto.getPhoneNumber())
                .verificationCode(userRegistrationDto.getVerificationCode())
                .build());
        NonClientRegistrationResponseDto responseDto = nonClientRegistrationService.registerNonClient(userRegistrationDto);
        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

    @Override
    public ResponseEntity<ClientRegistrationResponseDto> clientRegistration(ClientRegistrationRequestDto userRegistrationDto) {
        clientRegistrationService.validateClientRegistrationAttempt(userRegistrationDto);
        verificationCodeValidationRestTemplate.sendVerificationCodeValidationPostRequest(userRegistrationDto.getPhoneNumber(), userRegistrationDto.getVerificationCode()); //create sender bean
        kafkaTopicProducerService.publishVerificationCodeSetAsUsedTopicEvent(VerificationCodeSetAsUsedTopicMessage.builder()
                .publicationTimeStamp(new Date())
                .publicationReason(KafkaServiceConstant.VERIFICATION_CODE_GENERATION_REASON_CLIENT_REGISTRATION)
                .phoneNumber(userRegistrationDto.getPhoneNumber())
                .verificationCode(userRegistrationDto.getVerificationCode())
                .build());
        ClientRegistrationResponseDto responseDto = clientRegistrationService.registerClient(userRegistrationDto);
        return new ResponseEntity<>(responseDto, responseDto.getHttpStatus());
    }

}
