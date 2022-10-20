package dev.alexfossa204.starbank.registration.service.impl.registration;

import dev.alexfossa204.starbank.registration.service.dto.registration.NonClientRegistrationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.NonClientRegistrationResponseDto;
import dev.alexfossa204.starbank.registration.repository.model.Credential;
import dev.alexfossa204.starbank.registration.repository.model.Role;
import dev.alexfossa204.starbank.registration.repository.model.Notification;
import dev.alexfossa204.starbank.registration.repository.model.Passport;
import dev.alexfossa204.starbank.registration.repository.model.User;
import dev.alexfossa204.starbank.registration.repository.model.UserContact;
import dev.alexfossa204.starbank.registration.service.NonClientRegistrationService;
import dev.alexfossa204.starbank.registration.service.exception.CredentialAlreadyExistsException;
import dev.alexfossa204.starbank.registration.service.exception.PasswordsDontMatchException;
import dev.alexfossa204.starbank.registration.service.exception.UserRoleNotFoundException;
import dev.alexfossa204.starbank.registration.utils.uid.LongUidGenerator;
import dev.alexfossa204.starbank.registration.repository.*;
import dev.alexfossa204.starbank.registration.service.constant.RegistrationServiceConstant;
import dev.alexfossa204.starbank.registration.service.constant.ServiceExceptionConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Qualifier("nonClientRegistrationService")
public class NonClientRegistrationServiceImpl implements NonClientRegistrationService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final CredentialRepository credentialRepository;

    private final UserContactRepository userContactRepository;

    private final NotificationRepository notificationRepository;

    private final PassportRepository passportRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    @Override
    public NonClientRegistrationResponseDto registerNonClient(NonClientRegistrationRequestDto nonClientData) {
        Optional<Credential> credentialOptional = credentialRepository.findCredentialByPhoneLogin(nonClientData.getPhoneNumber());
        if(credentialOptional.isPresent()) {
            throw new CredentialAlreadyExistsException(String.format(ServiceExceptionConstant.CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED, nonClientData.getPhoneNumber()));
        }
        Optional<Role> roleOptional = roleRepository.findByRoleName(RegistrationServiceConstant.STANDART_CLIENT_ROLE);
        if(roleOptional.isEmpty()) {
            throw new UserRoleNotFoundException(String.format(ServiceExceptionConstant.ROLE_NOT_PRESENT_REGISTRATION_DENIED, RegistrationServiceConstant.STANDART_CLIENT_ROLE));
        }
        Passport passport = Passport.builder()
                .firstname(nonClientData.getFirstName())
                .lastname(nonClientData.getLastName())
                .surname(nonClientData.getMiddleName())
                .passportSerialNumber(nonClientData.getPassportNumber())
                .isUsResident(Boolean.valueOf(nonClientData.getIsUsResident()))
                .build();
        passportRepository.save(passport);
        User user = User.builder()
                .uid(LongUidGenerator.generateRandomTransferUid())
                .role(roleOptional.get())
                .passport(passport)
                .build();
        userRepository.save(user);
        UserContact userContact = UserContact.builder()
                .phoneNumber(nonClientData.getPhoneNumber())
                .user(user)
                .build();
        userContactRepository.save(userContact);
        String encodedPassword = passwordEncoder.encode(nonClientData.getNewPassword());
        Credential credential = Credential.builder()
                .phoneLogin(nonClientData.getPhoneNumber())
                .passportLogin(nonClientData.getPassportNumber())
                .password(encodedPassword)
                .registrationDate(new Date())
                .secretQuestion(nonClientData.getSecretQuestion())
                .secretQuestionAnswer(nonClientData.getSecretQuestionAnswer())
                .isActive(true)
                .isNonLocked(true)
                .isCredentialNonExpired(true)
                .user(user)
                .build();
        credentialRepository.save(credential);

        Notification notificationSettings = Notification.builder()
                .isEmailNotificationEnabled(false)
                .isPushNotificationEnabled(true)
                .isSmsNotificationEnabled(true)
                .user(user)
                .build();
        notificationRepository.save(notificationSettings);

        return NonClientRegistrationResponseDto.builder()
                .timeStamp(new Date())
                .httpStatus(HttpStatus.OK)
                .message(String.format(ServiceExceptionConstant.STATUS_CODE_OK_NEW_USER_REGISTERED_SUCCESSFULLY, userContact.getPhoneNumber(), credential.getPassportLogin()))
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public void validateNonClientRegistrationAttempt(NonClientRegistrationRequestDto nonClientData) {
        Optional<Credential> credentialOptional = credentialRepository.findCredentialByPhoneLoginOrPassportLogin(nonClientData.getPhoneNumber(), nonClientData.getPassportNumber());
        if(credentialOptional.isPresent()) {
            throw new CredentialAlreadyExistsException(String.format(ServiceExceptionConstant.CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED, nonClientData.getPhoneNumber()));
        }
        if (!StringUtils.equals(nonClientData.getNewPassword(), nonClientData.getConfirmPassword())) {
            throw new PasswordsDontMatchException(ServiceExceptionConstant.PASSWORDS_DONT_MATCH_MSG);
        }
    }

}
