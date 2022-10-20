package dev.alexfossa204.starbank.registration.unit.service;

import dev.alexfossa204.starbank.registration.service.dto.registration.NonClientRegistrationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.NonClientRegistrationResponseDto;
import dev.alexfossa204.starbank.registration.repository.model.Credential;
import dev.alexfossa204.starbank.registration.repository.model.Role;
import dev.alexfossa204.starbank.registration.repository.model.Notification;
import dev.alexfossa204.starbank.registration.repository.model.Passport;
import dev.alexfossa204.starbank.registration.repository.model.User;
import dev.alexfossa204.starbank.registration.repository.model.UserContact;
import dev.alexfossa204.starbank.registration.repository.*;
import dev.alexfossa204.starbank.registration.service.exception.CredentialAlreadyExistsException;
import dev.alexfossa204.starbank.registration.service.exception.PasswordsDontMatchException;
import dev.alexfossa204.starbank.registration.service.exception.UserRoleNotFoundException;
import dev.alexfossa204.starbank.registration.service.impl.registration.NonClientRegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static dev.alexfossa204.starbank.registration.service.constant.RegistrationServiceConstant.*;
import static dev.alexfossa204.starbank.registration.service.constant.ServiceExceptionConstant.*;
import static dev.alexfossa204.starbank.registration.ClientRegistrationConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpStatus.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NonClientRegistrationServiceTest {

    @Mock(lenient = true)
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CredentialRepository credentialRepository;

    @Mock
    private UserContactRepository userContactRepository;

    @Mock
    private PassportRepository passportRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private NonClientRegistrationServiceImpl nonClientRegistrationService;

    private NonClientRegistrationRequestDto correctNonClientRegistrationRequest;

    private NonClientRegistrationRequestDto incorrectNonClientRegistrationRequest;

    private NonClientRegistrationResponseDto successfulNonClientRegistrationResponse;

    private UserContact correctUserContactStub;

    private Credential correctCredentialStub;

    private Role correctUserRoleStub;

    @BeforeEach
    void beforeEach() {
        Date dateDummy = new Date();
        correctNonClientRegistrationRequest = NonClientRegistrationRequestDto.builder()
                .firstName(FIRSTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .middleName(MIDDLE_NAME_CORRECT_CHARS_DUMMY.getDummy())
                .lastName(LASTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .passportNumber(PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy())
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestion(SECRET_QUESTION_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestionAnswer(SECRET_QUESTION_ANSWER_CORRECT_CHARS_DUMMY.getDummy())
                .newPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .confirmPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .verificationCode(VERIFICATION_CODE_EMPTY_DUMMY.getDummy())
                .isUsResident(PASSPORT_IS_US_RESIDENT_TRUE.getDummy())
                .build();
        incorrectNonClientRegistrationRequest = NonClientRegistrationRequestDto.builder()
                .firstName(FIRSTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .middleName(MIDDLE_NAME_CORRECT_CHARS_DUMMY.getDummy())
                .lastName(LASTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .passportNumber(PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy())
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestion(SECRET_QUESTION_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestionAnswer(SECRET_QUESTION_ANSWER_CORRECT_CHARS_DUMMY.getDummy())
                .newPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .confirmPassword(PASSWORD_CORRECT_CHARS_V2_DUMMY.getDummy())
                .verificationCode(VERIFICATION_CODE_EMPTY_DUMMY.getDummy())
                .isUsResident(PASSPORT_IS_US_RESIDENT_TRUE.getDummy())
                .build();
        successfulNonClientRegistrationResponse = NonClientRegistrationResponseDto.builder()
                .timeStamp(dateDummy)
                .httpStatus(OK)
                .message(String.format(STATUS_CODE_OK_NEW_USER_REGISTERED_SUCCESSFULLY, PHONE_CORRECT_CHARS_DUMMY.getDummy(), PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy()))
                .build();
        Passport correctUserPassportStub = Passport.builder()
                .id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                .lastname(LASTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .firstname(FIRSTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .surname(MIDDLE_NAME_CORRECT_CHARS_DUMMY.getDummy())
                .birthdayDate(dateDummy)
                .issueDate(dateDummy)
                .expirationDate(dateDummy)
                .passportSerialNumber(PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy())
                .isUsResident(Boolean.valueOf(PASSPORT_IS_US_RESIDENT_TRUE.getDummy()))
                .build();
        correctUserRoleStub = Role.builder()
                .id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                .roleName(USER_ROLE_NAME_DUMMY.getDummy())
                .build();
        User correctUserStub = User.builder()
                .id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                .uid(Long.valueOf(USER_UID_CORRECT_DUMMY.getDummy()))
                .role(correctUserRoleStub)
                .passport(correctUserPassportStub)
                .imageUrl(IMAGE_URL_DUMMY.getDummy())
                .build();
        Notification notificationSettingsStub = Notification.builder()
                .isSmsNotificationEnabled(true)
                .isPushNotificationEnabled(true)
                .isEmailNotificationEnabled(true)
                .user(correctUserStub)
                .build();
        correctUserContactStub = UserContact.builder()
                .id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                .email(EMAIL_EMPTY_DUMMY.getDummy())
                .skype(SKYPE_EMPTY_DUMMY.getDummy())
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .user(correctUserStub)
                .build();
        correctCredentialStub = Credential.builder()
                .id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
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
        correctUserContactStub = UserContact.builder()
                .id(Long.valueOf(LONG_ID_DUMMY.getDummy()))
                .email(EMAIL_EMPTY_DUMMY.getDummy())
                .skype(SKYPE_EMPTY_DUMMY.getDummy())
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .user(correctUserStub)
                .build();
    }

    @Test
    void registerNonClientMethodPositiveTest() {
        when(credentialRepository.findCredentialByPhoneLogin(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.empty());
        when(roleRepository.findByRoleName(STANDART_CLIENT_ROLE))
                .thenReturn(Optional.of(correctUserRoleStub));
        when(passwordEncoder.encode(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy()))
                .thenReturn(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy());
        NonClientRegistrationResponseDto actualResponse = nonClientRegistrationService.registerNonClient(correctNonClientRegistrationRequest);
        assertThat(actualResponse).isEqualTo(successfulNonClientRegistrationResponse);
    }

    @Test
    void registerNonClientMethodCredentialAlreadyExistNegativeTest() {
        when(credentialRepository.findCredentialByPhoneLogin(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.of(correctCredentialStub));
        assertThatThrownBy(() -> nonClientRegistrationService.registerNonClient(correctNonClientRegistrationRequest))
                .isInstanceOf(CredentialAlreadyExistsException.class)
                .hasMessageStartingWith(String.format(CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED, PHONE_CORRECT_CHARS_DUMMY.getDummy()));
    }

    @Test
    void registerNonClientMethodRoleNotPresentExceptionNegativeTest() {
        when(credentialRepository.findCredentialByPhoneLogin(PHONE_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.empty());
        when(roleRepository.findByRoleName(STANDART_CLIENT_ROLE))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> nonClientRegistrationService.registerNonClient(correctNonClientRegistrationRequest))
                .isInstanceOf(UserRoleNotFoundException.class)
                .hasMessageStartingWith(String.format(ROLE_NOT_PRESENT_REGISTRATION_DENIED, STANDART_CLIENT_ROLE));
    }

    @Test
    void validateNonClientRegistrationAttemptNegativeTest() {
        when(credentialRepository.findCredentialByPhoneLoginOrPassportLogin(PHONE_CORRECT_CHARS_DUMMY.getDummy(), PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy()))
                .thenReturn(Optional.of(correctCredentialStub));
        assertThatThrownBy(() -> nonClientRegistrationService.validateNonClientRegistrationAttempt(correctNonClientRegistrationRequest))
                .isInstanceOf(CredentialAlreadyExistsException.class)
                .hasMessageStartingWith(String.format(CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED, PHONE_CORRECT_CHARS_DUMMY.getDummy()));
    }

    @Test
    void validateNonClientRegistrationAttemptPasswordsDontMatchNegativeTest() {
        assertThatThrownBy(() -> nonClientRegistrationService.validateNonClientRegistrationAttempt(incorrectNonClientRegistrationRequest))
                .isInstanceOf(PasswordsDontMatchException.class)
                .hasMessageStartingWith(PASSWORDS_DONT_MATCH_MSG);
    }

}
