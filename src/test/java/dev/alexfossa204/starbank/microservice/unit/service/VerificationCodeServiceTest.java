package dev.alexfossa204.starbank.microservice.unit.service;

import dev.alexfossa204.starbank.microservice.mapper.impl.UserContactToUserDtoMapper;
import dev.alexfossa204.starbank.microservice.service.dto.broker.UserDto;
import dev.alexfossa204.starbank.microservice.service.dto.verification.VerificationCodeGenerationResponseDto;
import dev.alexfossa204.starbank.microservice.repository.model.Role;
import dev.alexfossa204.starbank.microservice.repository.model.Passport;
import dev.alexfossa204.starbank.microservice.repository.model.User;
import dev.alexfossa204.starbank.microservice.repository.model.UserContact;
import dev.alexfossa204.starbank.microservice.repository.UserContactRepository;
import dev.alexfossa204.starbank.microservice.service.MessageBrokerTopicProducerService;
import dev.alexfossa204.starbank.microservice.service.impl.verification.VerificationCodeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Optional;

import static dev.alexfossa204.starbank.microservice.service.constant.ServiceExceptionConstant.STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_CLIENT;
import static dev.alexfossa204.starbank.microservice.service.constant.ServiceExceptionConstant.STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_NON_CLIENT;
import static dev.alexfossa204.starbank.microservice.ClientRegistrationConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerificationCodeServiceTest {

    @Mock
    private UserContactRepository userContactRepository;

    @Mock
    private MessageBrokerTopicProducerService messageBrokerTopicProducerService;

    @Mock
    private UserContactToUserDtoMapper userContactToUserDtoMapper;

    @InjectMocks
    private VerificationCodeServiceImpl verificationCodeService;

    private VerificationCodeGenerationResponseDto successfulVerificationCodeGenerationForNonClientStub;

    private VerificationCodeGenerationResponseDto successfulVerificationCodeGenerationForClientStub;

    private UserContact correctUserContactStub;

    @BeforeEach
    void beforeEachTest() {
        Date dateDummy = new Date();
        Passport correctUserPassportStub = Passport.builder()
                //.id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                .lastname(LASTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .firstname(FIRSTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .surname(MIDDLE_NAME_CORRECT_CHARS_DUMMY.getDummy())
                .birthdayDate(dateDummy)
                .issueDate(dateDummy)
                .expirationDate(dateDummy)
                .passportSerialNumber(PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy())
                .isUsResident(Boolean.valueOf(PASSPORT_IS_US_RESIDENT_TRUE.getDummy()))
                .build();
        Role correctUserRoleStub = Role.builder()
                //.id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                .roleName(USER_ROLE_NAME_DUMMY.getDummy())
                .build();
        User correctUserStub = User.builder()
                //.id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                //.uid(Long.valueOf(USER_UID_CORRECT_DUMMY.getDummy()))
                .role(correctUserRoleStub)
                .passport(correctUserPassportStub)
                .imageUrl(IMAGE_URL_DUMMY.getDummy())
                .build();
        correctUserContactStub = UserContact.builder()
                //.id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                .email(EMAIL_EMPTY_DUMMY.getDummy())
                .skype(SKYPE_EMPTY_DUMMY.getDummy())
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .user(correctUserStub)
                .build();
        successfulVerificationCodeGenerationForNonClientStub = VerificationCodeGenerationResponseDto.builder()
                .timeStamp(dateDummy)
                .httpStatus(HttpStatus.OK)
                .message(STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_NON_CLIENT)
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .isClient(Boolean.valueOf(IS_CLIENT_FALSE_DUMMY.getDummy()))
                .build();
        successfulVerificationCodeGenerationForClientStub = VerificationCodeGenerationResponseDto.builder()
                .timeStamp(dateDummy)
                .httpStatus(HttpStatus.OK)
                .message(STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_CLIENT)
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .isClient(Boolean.valueOf(IS_CLIENT_TRUE_DUMMY.getDummy()))
                .build();
    }

    @Test
    void generateVerificationCodeForNonClientPositiveTest() {
        when(userContactRepository.findByPhoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.empty());
        Optional<VerificationCodeGenerationResponseDto> actualResponse = verificationCodeService.generateVerificationCode(PHONE_CORRECT_CHARS_DUMMY.getDummy());
        assertThat(actualResponse.get()).isEqualTo(successfulVerificationCodeGenerationForNonClientStub);
    }

    @Test
    void generateVerificationCodeForClientPositiveTest() {
        when(userContactRepository.findByPhoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.of(correctUserContactStub));
        when(userContactToUserDtoMapper.mapEntityToDto(correctUserContactStub))
                .thenReturn(UserDto.builder().build());
        Optional<VerificationCodeGenerationResponseDto> actualResponse = verificationCodeService.generateVerificationCode(PHONE_CORRECT_CHARS_DUMMY.getDummy());
        assertThat(actualResponse.get()).isEqualTo(successfulVerificationCodeGenerationForClientStub);
    }

}
