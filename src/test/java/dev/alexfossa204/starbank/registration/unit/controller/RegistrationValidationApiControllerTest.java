package dev.alexfossa204.starbank.registration.unit.controller;

import dev.alexfossa204.starbank.registration.controller.impl.RegistrationValidationApiController;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationRequestDto;
import dev.alexfossa204.starbank.registration.service.dto.registration.ClientVerificationResponseDto;
import dev.alexfossa204.starbank.registration.service.ClientVerificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static dev.alexfossa204.starbank.registration.service.constant.ServiceExceptionConstant.*;
import static org.mockito.Mockito.*;
import static dev.alexfossa204.starbank.registration.ClientRegistrationConstant.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RegistrationValidationApiController.class)
@ExtendWith(MockitoExtension.class)
public class RegistrationValidationApiControllerTest {

    @MockBean
    private ClientVerificationService clientVerificationService;

    @Autowired
    private MockMvc mockMvc;

    private ClientVerificationRequestDto clientVerificationCorrectRequestDummy;

    private ClientVerificationResponseDto clientVerificationResponseClientRegistrationAccepted;

    private ClientVerificationResponseDto clientVerificationResponseClientRegistrationDenied;

    private ClientVerificationResponseDto clientVerificationResponseForNotRegisteredClientRegistrationAccepted;

    @BeforeEach
    void beforeEach() {
        Date dummyDate = new Date();
        clientVerificationCorrectRequestDummy = ClientVerificationRequestDto.builder()
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .build();
        clientVerificationResponseClientRegistrationDenied = ClientVerificationResponseDto.builder()
                .timeStamp(dummyDate)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED)
                .isClient(true)
                .build();
        clientVerificationResponseClientRegistrationAccepted = ClientVerificationResponseDto.builder()
                .timeStamp(dummyDate)
                .httpStatus(HttpStatus.OK)
                .message(CLIENT_PRESENT_BUT_DONT_HAS_CREDENTIALS_REGISTRATION_ACCEPTED)
                .isClient(true)
                .build();
        clientVerificationResponseForNotRegisteredClientRegistrationAccepted = ClientVerificationResponseDto.builder()
                .timeStamp(dummyDate)
                .httpStatus(HttpStatus.OK)
                .message(CLIENT_NOT_PRESENT_REGISTRATION_ACCEPTED)
                .isClient(false)
                .build();

    }

    @Test
    void verifyPhoneNumberCredentialForExistsAsClientNegativeTest() throws Exception {
        when(clientVerificationService.verifyClientForExists(clientVerificationCorrectRequestDummy))
                .thenReturn(clientVerificationResponseClientRegistrationDenied);
        mockMvc.perform(post(REGISTRATION_CLIENT_VALIDATION_URL.getDummy())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(clientVerificationCorrectRequestDummy)))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    void verifyPhoneNumberCredentialForExistsAsClientPositiveTest() throws Exception {
        when(clientVerificationService.verifyClientForExists(clientVerificationCorrectRequestDummy))
                .thenReturn(clientVerificationResponseClientRegistrationAccepted);
        mockMvc.perform(post(REGISTRATION_CLIENT_VALIDATION_URL.getDummy())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(clientVerificationCorrectRequestDummy)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void verifyPhoneNumberCredentialForExistsAsNonClientPositiveTest() throws Exception {
        when(clientVerificationService.verifyClientForExists(clientVerificationCorrectRequestDummy))
                .thenReturn(clientVerificationResponseForNotRegisteredClientRegistrationAccepted);
        mockMvc.perform(post(REGISTRATION_CLIENT_VALIDATION_URL.getDummy())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(clientVerificationCorrectRequestDummy)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }


}
