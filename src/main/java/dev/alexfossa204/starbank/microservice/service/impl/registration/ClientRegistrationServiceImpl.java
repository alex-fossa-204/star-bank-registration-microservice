package dev.alexfossa204.starbank.microservice.service.impl.registration;

import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientRegistrationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientRegistrationResponseDto;
import dev.alexfossa204.starbank.microservice.repository.model.Credential;
import dev.alexfossa204.starbank.microservice.repository.model.User;
import dev.alexfossa204.starbank.microservice.repository.model.UserContact;
import dev.alexfossa204.starbank.microservice.repository.CredentialRepository;
import dev.alexfossa204.starbank.microservice.repository.UserContactRepository;
import dev.alexfossa204.starbank.microservice.service.ClientRegistrationService;
import dev.alexfossa204.starbank.microservice.service.exception.CredentialAlreadyExistsException;
import dev.alexfossa204.starbank.microservice.service.exception.PasswordsDontMatchException;
import dev.alexfossa204.starbank.microservice.service.exception.UserContactNotFoundException;
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
import java.util.UUID;

import static dev.alexfossa204.starbank.microservice.service.constant.ServiceExceptionConstant.*;

@RequiredArgsConstructor
@Service
@Qualifier("clientRegistrationService")
public class ClientRegistrationServiceImpl implements ClientRegistrationService {

    private final UserContactRepository userContactRepository;

    private final CredentialRepository credentialRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    @Override
    public ClientRegistrationResponseDto registerClient(ClientRegistrationRequestDto clientData) {
        Optional<UserContact> userContactOptional = userContactRepository.findByPhoneNumber(clientData.getPhoneNumber());
        if(userContactOptional.isEmpty()) {
            throw new UserContactNotFoundException(String.format(USER_CONTACT_NOT_FOUND_MSG , clientData.getPhoneNumber()));
        }
        User user = userContactOptional.get().getUser();
        Credential credential = Credential.builder()
                .publicUuid(UUID.randomUUID())
                .phoneLogin(clientData.getPhoneNumber())
                .passportLogin(user.getPassport().getPassportSerialNumber())
                .password(passwordEncoder.encode(clientData.getNewPassword()))
                .registrationDate(new Date())
                .secretQuestion(clientData.getSecretQuestion())
                .secretQuestionAnswer(clientData.getSecretQuestionAnswer())
                .isActive(true)
                .isNonLocked(true)
                .isCredentialNonExpired(true)
                .user(user)
                .build();
        credentialRepository.save(credential);

        return ClientRegistrationResponseDto.builder()
                .timeStamp(new Date())
                .httpStatus(HttpStatus.OK)
                .message(String.format(STATUS_CODE_OK_NEW_USER_REGISTERED_SUCCESSFULLY, credential.getPhoneLogin(), credential.getPassportLogin()))
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public void validateClientRegistrationAttempt(ClientRegistrationRequestDto clientData) {
        Optional<Credential> credentialOptional = credentialRepository.findCredentialByPhoneLogin(clientData.getPhoneNumber());
        if(credentialOptional.isPresent()) {
            throw new CredentialAlreadyExistsException(String.format(CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED, clientData.getPhoneNumber()));
        }
        Optional<UserContact> userContactOptional = userContactRepository.findByPhoneNumber(clientData.getPhoneNumber());
        if(userContactOptional.isEmpty()) {
            throw new UserContactNotFoundException(String.format(USER_CONTACT_NOT_FOUND_MSG , clientData.getPhoneNumber()));
        }
        if (!StringUtils.equals(clientData.getNewPassword(), clientData.getConfirmPassword())) {
            throw new PasswordsDontMatchException(PASSWORDS_DONT_MATCH_MSG);
        }
    }

}
