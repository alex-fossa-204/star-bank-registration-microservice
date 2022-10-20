package dev.alexfossa204.starbank.registration.service.impl.registration;

import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationResponseDto;
import dev.alexfossa204.starbank.registration.repository.model.Credential;
import dev.alexfossa204.starbank.registration.repository.model.UserContact;
import dev.alexfossa204.starbank.registration.repository.CredentialRepository;
import dev.alexfossa204.starbank.registration.repository.UserContactRepository;
import dev.alexfossa204.starbank.registration.service.ClientVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static dev.alexfossa204.starbank.registration.service.constant.ServiceExceptionConstant.*;

@RequiredArgsConstructor
@Service
@Qualifier("clientVerificationService")
public class ClientVerificationServiceImpl implements ClientVerificationService {

    private final UserContactRepository userContactRepository;

    private final CredentialRepository credentialRepository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public ClientVerificationResponseDto verifyClientForExists(ClientVerificationRequestDto clientVerificationRequestDto) {
        Optional<ClientVerificationResponseDto> clientVerificationResponseDto = Optional.empty();
        Optional<Credential> credentialOptional = credentialRepository.findCredentialByPhoneLogin(clientVerificationRequestDto.getPhoneNumber());
        Optional<UserContact> userContactOptional = userContactRepository.findByPhoneNumber(clientVerificationRequestDto.getPhoneNumber());
        if(credentialOptional.isPresent()) {
            clientVerificationResponseDto = Optional.of(ClientVerificationResponseDto.builder()
                            .timeStamp(new Date())
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message(CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED)
                            .isClient(true)
                            .build());
        }
        if (credentialOptional.isEmpty() && userContactOptional.isPresent()) {
            clientVerificationResponseDto = Optional.of(ClientVerificationResponseDto.builder()
                    .timeStamp(new Date())
                    .httpStatus(HttpStatus.OK)
                    .message(CLIENT_PRESENT_BUT_DONT_HAS_CREDENTIALS_REGISTRATION_ACCEPTED)
                    .isClient(true)
                    .build());
        }
        if(credentialOptional.isEmpty() && userContactOptional.isEmpty()) {
            clientVerificationResponseDto = Optional.of(ClientVerificationResponseDto.builder()
                    .timeStamp(new Date())
                    .httpStatus(HttpStatus.OK)
                    .message(CLIENT_NOT_PRESENT_REGISTRATION_ACCEPTED)
                    .isClient(false)
                    .build());
        }
        return clientVerificationResponseDto.get();
    }

}