package dev.alexfossa204.starbank.microservice.unit.controller;

import dev.alexfossa204.starbank.microservice.controller.impl.VerificationApiController;
import dev.alexfossa204.starbank.microservice.service.dto.verification.VerificationCodeGenerationRequestDto;
import dev.alexfossa204.starbank.microservice.service.dto.verification.VerificationCodeGenerationResponseDto;
import dev.alexfossa204.starbank.microservice.service.VerificationCodeService;
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
import java.util.Optional;

import static dev.alexfossa204.starbank.microservice.service.constant.ServiceExceptionConstant.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static dev.alexfossa204.starbank.microservice.ClientRegistrationConstant.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VerificationApiController.class)
@ExtendWith(MockitoExtension.class)
public class VerificationApiControllerTest {

    @MockBean
    private VerificationCodeService verificationCodeService;

    @Autowired
    private MockMvc mockMvc;

    private VerificationCodeGenerationRequestDto verificationCodeGenerationCorrectRequest;

    private VerificationCodeGenerationResponseDto verificationCodeGenerationCorrectResponseForNonClient;

    private VerificationCodeGenerationResponseDto verificationCodeGenerationCorrectResponseForClient;

    @BeforeEach
    void beforeTests() {
        Date dummyDate = new Date();
        verificationCodeGenerationCorrectRequest = VerificationCodeGenerationRequestDto.builder()
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .build();
        verificationCodeGenerationCorrectResponseForNonClient = VerificationCodeGenerationResponseDto.builder()
                .timeStamp(dummyDate)
                .httpStatus(HttpStatus.OK)
                .message(STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_NON_CLIENT)
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .isClient(false)
                .build();
        verificationCodeGenerationCorrectResponseForClient = VerificationCodeGenerationResponseDto.builder()
                .timeStamp(new Date())
                .httpStatus(HttpStatus.OK)
                .message(STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_CLIENT)
                .phoneNumber(PHONE_CORRECT_CHARS_DUMMY.getDummy())
                .isClient(true)
                .build();
    }

    @Test
    void sendRequestForVerificationCodeGenerationForNonClientPositiveTest() throws Exception {
        when(verificationCodeService.generateVerificationCode(verificationCodeGenerationCorrectRequest.getPhoneNumber()))
                .thenReturn(Optional.of(verificationCodeGenerationCorrectResponseForNonClient));
        mockMvc.perform(post(REGISTRATION_SEND_REQUEST_FOR_CODE_GENERATION_URL.getDummy())
                        .contentType(APPLICATION_PROBLEM_JSON_VALUE)
                        .accept(APPLICATION_PROBLEM_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(verificationCodeGenerationCorrectRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void sendRequestForVerificationCodeGenerationForClientPositiveTest() throws Exception {
        when(verificationCodeService.generateVerificationCode(verificationCodeGenerationCorrectRequest.getPhoneNumber()))
                .thenReturn(Optional.of(verificationCodeGenerationCorrectResponseForClient));
        mockMvc.perform(post(REGISTRATION_SEND_REQUEST_FOR_CODE_GENERATION_URL.getDummy())
                        .contentType(APPLICATION_PROBLEM_JSON_VALUE)
                        .accept(APPLICATION_PROBLEM_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(verificationCodeGenerationCorrectResponseForClient)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void sendRequestForVerificationCodeGenerationCodeGeneratorCrashedNegativeTest() throws Exception {
        when(verificationCodeService.generateVerificationCode(verificationCodeGenerationCorrectRequest.getPhoneNumber()))
                .thenReturn(Optional.empty());
        mockMvc.perform(post(REGISTRATION_SEND_REQUEST_FOR_CODE_GENERATION_URL.getDummy())
                        .contentType(APPLICATION_PROBLEM_JSON_VALUE)
                        .accept(APPLICATION_PROBLEM_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(verificationCodeGenerationCorrectResponseForClient)))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }
}
