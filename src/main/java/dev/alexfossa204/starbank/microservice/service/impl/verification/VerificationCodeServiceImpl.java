package dev.alexfossa204.starbank.microservice.service.impl.verification;

import dev.alexfossa204.starbank.microservice.mapper.impl.UserContactToUserDtoMapper;
import dev.alexfossa204.starbank.microservice.service.dto.broker.UserDto;
import dev.alexfossa204.starbank.microservice.service.dto.broker.VerificationCodeGenerationTopicMessage;
import dev.alexfossa204.starbank.microservice.service.dto.verification.VerificationCodeGenerationResponseDto;
import dev.alexfossa204.starbank.microservice.repository.model.UserContact;
import dev.alexfossa204.starbank.microservice.repository.UserContactRepository;
import dev.alexfossa204.starbank.microservice.service.MessageBrokerTopicProducerService;
import dev.alexfossa204.starbank.microservice.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static dev.alexfossa204.starbank.microservice.service.constant.ServiceExceptionConstant.*;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final UserContactRepository userContactRepository;

    private final MessageBrokerTopicProducerService messageBrokerTopicProducerService;

    private final UserContactToUserDtoMapper userContactToUserDtoMapper;

    @Override
    public Optional<VerificationCodeGenerationResponseDto> generateVerificationCode(String phoneNumber) {
        Optional<UserContact> contactOptional = userContactRepository.findByPhoneNumber(phoneNumber);
        Optional<VerificationCodeGenerationResponseDto> verificationCodeResponseDtoOptional = Optional.empty();
        boolean isClient;
        if (contactOptional.isEmpty()) {
            isClient = false;
            Date verificationCodeGenerationTimeStamp = new Date();
            verificationCodeResponseDtoOptional = Optional.of(VerificationCodeGenerationResponseDto.builder()
                    .timeStamp(verificationCodeGenerationTimeStamp)
                    .httpStatus(HttpStatus.OK)
                    .message(STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_NON_CLIENT)
                    .phoneNumber(phoneNumber)
                    .isClient(isClient)
                    .build());
            UserDto userDto = UserDto.builder()
                    .phoneLogin(phoneNumber)
                    .isClient(isClient)
                    .build();
            VerificationCodeGenerationTopicMessage topicMessage = VerificationCodeGenerationTopicMessage.builder()
                    .publicationReason(NON_CLIENT_REGISTRATION_ATTEMPT)
                    .publicationTimeStamp(verificationCodeGenerationTimeStamp)
                    .user(userDto)
                    .build();
            messageBrokerTopicProducerService.publishVerificationCodeGenerationTopicEvent(topicMessage);

        }
        if (contactOptional.isPresent()) {
            isClient = true;
            Date verificationCodeGenerationTimeStamp = new Date();
            verificationCodeResponseDtoOptional = Optional.of(VerificationCodeGenerationResponseDto.builder()
                    .timeStamp(verificationCodeGenerationTimeStamp)
                    .httpStatus(HttpStatus.OK)
                    .message(STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_CLIENT)
                    .phoneNumber(phoneNumber)
                    .isClient(isClient)
                    .build());
            UserDto userDto = userContactToUserDtoMapper.mapEntityToDto(contactOptional.get());
            userDto.setIsClient(isClient);

            VerificationCodeGenerationTopicMessage topicMessage = VerificationCodeGenerationTopicMessage.builder()
                    .publicationReason(CLIENT_REGISTRATION_ATTEMPT)
                    .publicationTimeStamp(verificationCodeGenerationTimeStamp)
                    .user(userDto)
                    .build();
            messageBrokerTopicProducerService.publishVerificationCodeGenerationTopicEvent(topicMessage);
        }
        return verificationCodeResponseDtoOptional;
    }

}
