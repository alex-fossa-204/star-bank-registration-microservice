package dev.alexfossa204.starbank.registration.unit.service;

import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationResponseDto;
import dev.alexfossa204.starbank.registration.repository.model.Credential;
import dev.alexfossa204.starbank.registration.repository.model.Role;
import dev.alexfossa204.starbank.registration.repository.model.Passport;
import dev.alexfossa204.starbank.registration.repository.model.User;
import dev.alexfossa204.starbank.registration.repository.model.UserContact;
import dev.alexfossa204.starbank.registration.repository.CredentialRepository;
import dev.alexfossa204.starbank.registration.repository.UserContactRepository;
import dev.alexfossa204.starbank.registration.service.impl.registration.ClientVerificationServiceImpl;
import dev.alexfossa204.starbank.registration.ClientRegistrationConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static dev.alexfossa204.starbank.registration.service.constant.ServiceExceptionConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientVerificationServiceTest {

    @Mock
    private UserContactRepository userContactRepository;

    @Mock
    private CredentialRepository credentialRepository;

    @InjectMocks
    private ClientVerificationServiceImpl clientVerificationService;

    private ClientVerificationRequestDto correctClientVerificationRequest;

    private UserContact correctUserContactStub;

    private ClientVerificationResponseDto unsuccessfulResponseClientRegistered;

    private ClientVerificationResponseDto successfulResponseClientNotRegistered;

    private ClientVerificationResponseDto successfulResponseClientNotExist;

    private Credential correctCredentialStub;

    @BeforeEach
    void beforeEachTest() {
        Date dateDummy = new Date();
        correctClientVerificationRequest = ClientVerificationRequestDto.builder()
                .phoneNumber(ClientRegistrationConstant.PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .build();
        unsuccessfulResponseClientRegistered = ClientVerificationResponseDto.builder()
                .timeStamp(dateDummy)
                .httpStatus(BAD_REQUEST)
                .message(CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED)
                .isClient(true)
                .build();
        successfulResponseClientNotRegistered = ClientVerificationResponseDto.builder()
                .timeStamp(dateDummy)
                .httpStatus(OK)
                .message(CLIENT_PRESENT_BUT_DONT_HAS_CREDENTIALS_REGISTRATION_ACCEPTED)
                .isClient(true)
                .build();
        successfulResponseClientNotExist = ClientVerificationResponseDto.builder()
                .timeStamp(dateDummy)
                .httpStatus(OK)
                .message(CLIENT_NOT_PRESENT_REGISTRATION_ACCEPTED)
                .isClient(false)
                .build();
        Passport correctUserPassportStub = Passport.builder()
                //.id(Long.valueOf(ClientRegistrationConstant.LONG_ID_DUMMY.getDummy()))
                .lastname(ClientRegistrationConstant.LASTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .firstname(ClientRegistrationConstant.FIRSTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .surname(ClientRegistrationConstant.MIDDLE_NAME_CORRECT_CHARS_DUMMY.getDummy())
                .birthdayDate(dateDummy)
                .issueDate(dateDummy)
                .expirationDate(dateDummy)
                .passportSerialNumber(ClientRegistrationConstant.PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy())
                .isUsResident(Boolean.valueOf(ClientRegistrationConstant.PASSPORT_IS_US_RESIDENT_TRUE.getDummy()))
                .build();
        Role correctUserRoleStub = Role.builder()
                //.id(Long.valueOf(ClientRegistrationConstant.LONG_ID_DUMMY.getDummy()))
                .roleName(ClientRegistrationConstant.USER_ROLE_NAME_DUMMY.getDummy())
                .build();
        User correctUserStub = User.builder()
                //.id(Long.valueOf(ClientRegistrationConstant.LONG_ID_DUMMY.getDummy()))
                //.uid(Long.valueOf(ClientRegistrationConstant.USER_UID_CORRECT_DUMMY.getDummy()))
                .role(correctUserRoleStub)
                .passport(correctUserPassportStub)
                .imageUrl(ClientRegistrationConstant.IMAGE_URL_DUMMY.getDummy())
                .build();
        correctUserContactStub = UserContact.builder()
                //.id(Long.valueOf(ClientRegistrationConstant.LONG_ID_DUMMY.getDummy()))
                .email(ClientRegistrationConstant.EMAIL_EMPTY_DUMMY.getDummy())
                .skype(ClientRegistrationConstant.SKYPE_EMPTY_DUMMY.getDummy())
                .phoneNumber(ClientRegistrationConstant.PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .user(correctUserStub)
                .build();
        correctCredentialStub = Credential.builder()
                //.id(Long.valueOf(ClientRegistrationConstant.LONG_ID_DUMMY.getDummy()))
                .phoneLogin(ClientRegistrationConstant.PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .passportLogin(ClientRegistrationConstant.PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy())
                .password(ClientRegistrationConstant.PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .registrationDate(dateDummy)
                .lastLoginDate(dateDummy)
                .secretQuestion(ClientRegistrationConstant.SECRET_QUESTION_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestionAnswer(ClientRegistrationConstant.SECRET_QUESTION_ANSWER_CORRECT_CHARS_DUMMY.getDummy())
                .isActive(Boolean.valueOf(ClientRegistrationConstant.IS_USER_ACTIVE_TRUE_DUMMY.getDummy()))
                .isCredentialNonExpired(Boolean.valueOf(ClientRegistrationConstant.IS_CREDENTIAL_NON_EXPIRED_TRUE.getDummy()))
                .user(correctUserStub)
                .build();
        correctUserContactStub = UserContact.builder()
                //.id(Long.valueOf(ClientRegistrationConstant.LONG_ID_DUMMY.getDummy()))
                .email(ClientRegistrationConstant.EMAIL_EMPTY_DUMMY.getDummy())
                .skype(ClientRegistrationConstant.SKYPE_EMPTY_DUMMY.getDummy())
                .phoneNumber(ClientRegistrationConstant.PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .user(correctUserStub)
                .build();

    }

    @Test
    void verifyClientForExistsMethodClientPresentButHasCredentialsNegativeTest() {
        when(credentialRepository.findCredentialByPhoneLogin(ClientRegistrationConstant.PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.of(correctCredentialStub));
        ClientVerificationResponseDto actualResult = clientVerificationService.verifyClientForExists(correctClientVerificationRequest);
        assertThat(actualResult).isEqualTo(unsuccessfulResponseClientRegistered);
    }

    @Test
    void verifyClientForExistsMethodClientPresentButDontHasCredentialsNegativeTest() {
        when(credentialRepository.findCredentialByPhoneLogin(ClientRegistrationConstant.PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.empty());
        when(userContactRepository.findByPhoneNumber(ClientRegistrationConstant.PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.of(correctUserContactStub));
        ClientVerificationResponseDto actualResult = clientVerificationService.verifyClientForExists(correctClientVerificationRequest);
        assertThat(actualResult).isEqualTo(successfulResponseClientNotRegistered);
    }

    @Test
    void verifyClientForExistsMethodClientNotPresentNegativeTest() {
        when(credentialRepository.findCredentialByPhoneLogin(ClientRegistrationConstant.PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.empty());
        when(userContactRepository.findByPhoneNumber(ClientRegistrationConstant.PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.empty());
        ClientVerificationResponseDto actualResult = clientVerificationService.verifyClientForExists(correctClientVerificationRequest);
        assertThat(actualResult).isEqualTo(successfulResponseClientNotExist);
    }

}
