package dev.alexfossa204.starbank.microservice.utils.json;

import dev.alexfossa204.starbank.microservice.service.dto.verification.VerificationCodeCheckResponseDto;
import dev.alexfossa204.starbank.microservice.service.exception.VerificationCodeNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexfossa204.starbank.microservice.service.constant.ServiceExceptionConstant;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Qualifier("verificationCodeCheckResponseDtoCustomJsonParser")
@AllArgsConstructor
public class VerificationCodeCheckResponseDtoCustomJsonParser implements CustomJsonParser<VerificationCodeCheckResponseDto> {

    private final ObjectMapper objectMapper;

    @Override
    public VerificationCodeCheckResponseDto parseJsonString(String jsonString) {
        Optional<VerificationCodeCheckResponseDto> codeCheckResponseDtoOptional = Optional.empty();
        try {
            VerificationCodeCheckResponseDto dto = objectMapper.readValue(jsonString, VerificationCodeCheckResponseDto.class);
            codeCheckResponseDtoOptional = Optional.of(dto);
        } catch (JsonProcessingException e) {
            if(codeCheckResponseDtoOptional.isEmpty()) {
                throw new VerificationCodeNotFoundException(ServiceExceptionConstant.VERIFICATION_CODE_NOT_FOUND_MSG);
            }
        }
        return codeCheckResponseDtoOptional.get();
    }
}
