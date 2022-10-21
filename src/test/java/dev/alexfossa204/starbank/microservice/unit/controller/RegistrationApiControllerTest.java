package dev.alexfossa204.starbank.microservice.unit.controller;

import dev.alexfossa204.starbank.microservice.controller.impl.RegistrationApiController;
import dev.alexfossa204.starbank.microservice.controller.rest.VerificationCodeValidationRestTemplate;
import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientRegistrationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.ClientRegistrationResponseDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.NonClientRegistrationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.registration.NonClientRegistrationResponseDto;
import dev.alexfossa204.starbank.microservice.service.ClientRegistrationService;
import dev.alexfossa204.starbank.microservice.service.NonClientRegistrationService;
import dev.alexfossa204.starbank.microservice.service.exception.CredentialAlreadyExistsException;
import dev.alexfossa204.starbank.microservice.service.exception.PasswordsDontMatchException;
import dev.alexfossa204.starbank.microservice.service.impl.broker.KafkaTopicProducerService;
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

import static dev.alexfossa204.starbank.microservice.service.constant.ServiceExceptionConstant.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static dev.alexfossa204.starbank.microservice.ClientRegistrationConstant.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.HttpStatus.*;

@WebMvcTest(value = RegistrationApiController.class)
@ExtendWith(MockitoExtension.class)
public class RegistrationApiControllerTest {

    @MockBean
    private NonClientRegistrationService nonClientRegistrationService;

    @MockBean
    private ClientRegistrationService clientRegistrationService;

    @MockBean
    private KafkaTopicProducerService kafkaTopicProducerService;

    @MockBean
    private VerificationCodeValidationRestTemplate verificationCodeValidationRestTemplate;

    @Autowired
    private MockMvc mockMvc;

    private ClientRegistrationRequestDto clientRegistrationCorrectRequestBodyDummy;

    private NonClientRegistrationRequestDto nonClientRegistrationCorrectRequestBodyDummy;

    private ClientRegistrationResponseDto clientRegistrationResponseOk;
    private NonClientRegistrationResponseDto nonClientRegistrationResponseOk;

    @BeforeEach
    void beforeEach() {
        Date dateDummy = new Date();
        clientRegistrationCorrectRequestBodyDummy = ClientRegistrationRequestDto.builder()
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .newPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .confirmPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .secretQuestion(SECRET_QUESTION_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestionAnswer(SECRET_QUESTION_ANSWER_CORRECT_CHARS_DUMMY.getDummy())
                .verificationCode(VERIFICATION_CODE_CORRECT_CHARS_DUMMY.getDummy())
                .build();
        clientRegistrationResponseOk = ClientRegistrationResponseDto.builder()
                .timeStamp(dateDummy)
                .httpStatus(OK)
                .message(STATUS_CODE_OK_NEW_USER_REGISTERED_SUCCESSFULLY)
                .build();
        nonClientRegistrationCorrectRequestBodyDummy = NonClientRegistrationRequestDto.builder()
                .firstName(FIRSTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .middleName(MIDDLE_NAME_CORRECT_CHARS_DUMMY.getDummy())
                .lastName(LASTNAME_CORRECT_CHARS_DUMMY.getDummy())
                .passportNumber(PASSPORT_NUMBER_CORRECT_CHARS_DUMMY.getDummy())
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestion(SECRET_QUESTION_CORRECT_CHARS_DUMMY.getDummy())
                .secretQuestionAnswer(SECRET_QUESTION_ANSWER_CORRECT_CHARS_DUMMY.getDummy())
                .newPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .confirmPassword(PASSWORD_CORRECT_CHARS_V1_DUMMY.getDummy())
                .verificationCode(VERIFICATION_CODE_CORRECT_CHARS_DUMMY.getDummy())
                .isUsResident(PASSPORT_IS_US_RESIDENT_TRUE.getDummy())
                .build();
        nonClientRegistrationResponseOk = NonClientRegistrationResponseDto.builder()
                .timeStamp(new Date())
                .httpStatus(HttpStatus.OK)
                .message(String.format(STATUS_CODE_OK_NEW_USER_REGISTERED_SUCCESSFULLY, nonClientRegistrationCorrectRequestBodyDummy.getPhoneNumber(), nonClientRegistrationCorrectRequestBodyDummy.getPassportNumber()))
                .build();
    }

    @Test
    void registerClientPostMethodPositiveTest() throws Exception {
        when(clientRegistrationService.registerClient(clientRegistrationCorrectRequestBodyDummy))
                .thenReturn(clientRegistrationResponseOk);
        mockMvc.perform(post(REGISTRATION_CLIENT_URL.getDummy())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(clientRegistrationCorrectRequestBodyDummy)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void registerClientPostMethodNegativeTest() throws Exception {
        doThrow(new CredentialAlreadyExistsException(String.format(CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED, clientRegistrationCorrectRequestBodyDummy.getPhoneNumber())))
                .when(clientRegistrationService).validateClientRegistrationAttempt(clientRegistrationCorrectRequestBodyDummy);
        mockMvc.perform(post(REGISTRATION_CLIENT_URL.getDummy())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(clientRegistrationCorrectRequestBodyDummy)))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    void registerClientPostMethodPasswordsDontMatchNegativeTest() throws Exception {
        doThrow(new PasswordsDontMatchException(PASSWORDS_DONT_MATCH_MSG))
                .when(clientRegistrationService).validateClientRegistrationAttempt(clientRegistrationCorrectRequestBodyDummy);
        mockMvc.perform(post(REGISTRATION_CLIENT_URL.getDummy())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(clientRegistrationCorrectRequestBodyDummy)))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    void registerNonClientPositiveTest() throws Exception {
        when(nonClientRegistrationService.registerNonClient(nonClientRegistrationCorrectRequestBodyDummy))
                .thenReturn(nonClientRegistrationResponseOk);
        mockMvc.perform(post(REGISTRATION_NON_CLIENT_URL.getDummy())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(nonClientRegistrationCorrectRequestBodyDummy)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void registerNonClientNegativeTest() throws Exception {
        doThrow(new CredentialAlreadyExistsException(String.format(CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED, clientRegistrationCorrectRequestBodyDummy.getPhoneNumber())))
                .when(nonClientRegistrationService).validateNonClientRegistrationAttempt(nonClientRegistrationCorrectRequestBodyDummy);
        mockMvc.perform(post(REGISTRATION_NON_CLIENT_URL.getDummy())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(nonClientRegistrationCorrectRequestBodyDummy)))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    void registerNonClientPasswordsDontMatchNegativeTest() throws Exception {
        doThrow(new PasswordsDontMatchException(PASSWORDS_DONT_MATCH_MSG))
                .when(nonClientRegistrationService).validateNonClientRegistrationAttempt(nonClientRegistrationCorrectRequestBodyDummy);
        mockMvc.perform(post(REGISTRATION_NON_CLIENT_URL.getDummy())
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(nonClientRegistrationCorrectRequestBodyDummy)))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

}
