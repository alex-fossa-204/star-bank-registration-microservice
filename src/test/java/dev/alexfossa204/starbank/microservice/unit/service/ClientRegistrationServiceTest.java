package dev.alexfossa204.starbank.microservice.unit.service;

import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientRegistrationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientRegistrationResponseDto;
import dev.alexfossa204.starbank.microservice.repository.model.Credential;
import dev.alexfossa204.starbank.microservice.repository.model.Role;
import dev.alexfossa204.starbank.microservice.repository.model.Passport;
import dev.alexfossa204.starbank.microservice.repository.model.User;
import dev.alexfossa204.starbank.microservice.repository.model.UserContact;
import dev.alexfossa204.starbank.microservice.repository.CredentialRepository;
import dev.alexfossa204.starbank.microservice.repository.UserContactRepository;
import dev.alexfossa204.starbank.microservice.service.exception.CredentialAlreadyExistsException;
import dev.alexfossa204.starbank.microservice.service.exception.PasswordsDontMatchException;
import dev.alexfossa204.starbank.microservice.service.exception.UserContactNotFoundException;
import dev.alexfossa204.starbank.microservice.service.impl.registration.ClientRegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static dev.alexfossa204.starbank.microservice.service.constant.ServiceExceptionConstant.*;
import static dev.alexfossa204.starbank.microservice.ClientRegistrationConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
public class ClientRegistrationServiceTest {

    @Mock
    private UserContactRepository userContactRepository;

    @Mock
    private CredentialRepository credentialRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientRegistrationServiceImpl clientRegistrationService;

    private ClientRegistrationRequestDto correctClientRequestDtoDummy;

    private ClientRegistrationRequestDto incorrectClientRequestDtoDummy;

    private ClientRegistrationResponseDto successfulClientRegistrationResponseStub;

    private Credential correctCredential;

    private UserContact correctUserContactStub;

    @BeforeEach
    void beforeEachTest() {
        Date dateDummy = new Date();
        correctClientRequestDtoDummy = ClientRegistrationRequestDto.builder()
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .newPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .confirmPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .secretQuestion(SECRET_QUESTION_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestionAnswer(SECRET_QUESTION_ANSWER_CORRECT_CHARS_DUMMY.getDummy())
                .verificationCode(VERIFICATION_CODE_CORRECT_CHARS_DUMMY.getDummy())
                .build();
        incorrectClientRequestDtoDummy = ClientRegistrationRequestDto.builder()
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .newPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .confirmPassword(PASSWORD_CORRECT_CHARS_V2_DUMMY.getDummy())
                .secretQuestion(SECRET_QUESTION_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestionAnswer(SECRET_QUESTION_ANSWER_CORRECT_CHARS_DUMMY.getDummy())
                .verificationCode(VERIFICATION_CODE_CORRECT_CHARS_DUMMY.getDummy())
                .build();
        successfulClientRegistrationResponseStub = ClientRegistrationResponseDto.builder()
                .timeStamp(dateDummy)
                .httpStatus(OK)
                .message(String.format(STATUS_CODE_OK_NEW_USER_REGISTERED_SUCCESSFULLY, PHONE_CORRECT_CHARS_DUMMY.getDummy(), PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy()))
                .build();
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
        correctCredential = Credential.builder()
                //.id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                .phoneLogin(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .passportLogin(PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy())
                .password(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .registrationDate(dateDummy)
                .lastLoginDate(dateDummy)
                .secretQuestion(SECRET_QUESTION_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestionAnswer(SECRET_QUESTION_ANSWER_CORRECT_CHARS_DUMMY.getDummy())
                .isActive(Boolean.valueOf(IS_USER_ACTIVE_TRUE_DUMMY.getDummy()))
                .isCredentialNonExpired(Boolean.valueOf(IS_CREDENTIAL_NON_EXPIRED_TRUE.getDummy()))
                .user(correctUserStub)
                .build();

    }

    @Test
    void registerClientMethodPositiveTest() {
        when(userContactRepository.findByPhoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.of(correctUserContactStub));
        when(passwordEncoder.encode(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy()))
                .thenReturn(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy());
        ClientRegistrationResponseDto actualResponse = clientRegistrationService.registerClient(correctClientRequestDtoDummy);
        assertThat(actualResponse).isEqualTo(successfulClientRegistrationResponseStub);
    }

    @Test
    void registerClientMethodNegativeTest() {
        when(userContactRepository.findByPhoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> clientRegistrationService.registerClient(correctClientRequestDtoDummy))
                .isInstanceOf(UserContactNotFoundException.class)
                .hasMessageStartingWith(String.format(USER_CONTACT_NOT_FOUND_MSG, PHONE_CORRECT_CHARS_DUMMY.getDummy()));
    }

    @Test
    void validateClientRegistrationAttemptNegativeTestAssertionWithCredentialAlreadyExistException() {
        when(credentialRepository.findCredentialByPhoneLogin(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.of(correctCredential));
        assertThatThrownBy(() -> clientRegistrationService.validateClientRegistrationAttempt(correctClientRequestDtoDummy))
                .isInstanceOf(CredentialAlreadyExistsException.class)
                .hasMessageStartingWith(String.format(CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED, PHONE_CORRECT_CHARS_DUMMY.getDummy()));
    }

    @Test
    void validateClientRegistrationAttemptNegativeTestAssertionWithUserContactNotFoundException() {
        when(userContactRepository.findByPhoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> clientRegistrationService.validateClientRegistrationAttempt(correctClientRequestDtoDummy))
                .isInstanceOf(UserContactNotFoundException.class)
                .hasMessageStartingWith(String.format(USER_CONTACT_NOT_FOUND_MSG , PHONE_CORRECT_CHARS_DUMMY.getDummy()));
    }

    @Test
    void validateClientRegistrationAttemptPasswordsDontMatchNegativeTest() {
        when(credentialRepository.findCredentialByPhoneLogin(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.empty());
        when(userContactRepository.findByPhoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.of(correctUserContactStub));
        assertThatThrownBy(() -> clientRegistrationService.validateClientRegistrationAttempt(incorrectClientRequestDtoDummy))
                .isInstanceOf(PasswordsDontMatchException.class)
                .hasMessageStartingWith(PASSWORDS_DONT_MATCH_MSG);
    }

}
