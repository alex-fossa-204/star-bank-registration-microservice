package dev.alexfossa204.starbank.microservice.controller;

import dev.alexfossa204.starbank.microservice.service.dto.auth.HttpResponseDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.alexfossa204.starbank.microservice.service.exception.*;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.NoResultException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static dev.alexfossa204.starbank.microservice.service.constant.ServiceExceptionConstant.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public interface ErrorHandlerApi extends ErrorController {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(NOT_FOUND)
    default ResponseEntity<HttpResponseDto> noHandlerFoundExceptionHandler() {
        return createHttpResponse(NOT_FOUND, STATUS_CODE_NOT_FOUND_CODE_URL_HANDLER_NOT_EXISTS);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(UNAUTHORIZED)
    default ResponseEntity<HttpResponseDto> badCredentialsExceptionHandler() {
        return createHttpResponse(UNAUTHORIZED, STATUS_CODE_BAD_REQUEST_INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(value = NoResultException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> notFoundExceptionHandler(NoResultException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> unsupportedOperationExceptionHandler(UnsupportedOperationException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    default ResponseEntity<HttpResponseDto> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException exception) {
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format("%s %s", exception.getMessage(), METHOD_NOT_SUPPORTED));
    }

    @ExceptionHandler(value = UnsupportedAuthorizationMethodException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> unsupportedAuthorizationMethodExceptionHandler(UnsupportedAuthorizationMethodException serviceException) {
        return createHttpResponse(BAD_REQUEST, serviceException.getMessage());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> userNameNotFoundExceptionHandler(UsernameNotFoundException serviceException) {
        return createHttpResponse(BAD_REQUEST, serviceException.getMessage());
    }

    @ExceptionHandler(value = PasswordsDontMatchException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> passwordsDontMatchExceptionHandler(PasswordsDontMatchException serviceException) {
        return createHttpResponse(BAD_REQUEST, serviceException.getMessage());
    }

    @ExceptionHandler(value = VerificationCodeNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> verificationCodeNotFoundExceptionHandler(VerificationCodeNotFoundException serviceException) {
        return createHttpResponse(BAD_REQUEST, serviceException.getMessage());
    }

    @ExceptionHandler(value = VerificationCodeExpiredException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> verificationCodeExpiredExceptionHandler(VerificationCodeExpiredException serviceException) {
        return createHttpResponse(BAD_REQUEST, serviceException.getMessage());
    }

    @ExceptionHandler(value = SQLException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> duplicateKeyValueExceptionHandler(SQLException psqlException) {
        return createHttpResponse(BAD_REQUEST, CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED);
    }

    @ExceptionHandler(value = UserContactNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> userContactNotFoundExceptionHandler(UserContactNotFoundException serviceException) {
        return createHttpResponse(BAD_REQUEST, serviceException.getMessage());
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> noSuchElementExceptionHandler(NoSuchElementException serviceException) {
        return createHttpResponse(BAD_REQUEST, serviceException.getMessage());
    }

    @ExceptionHandler(value = CredentialAlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> credentialAlreadyExistsExceptionHandler(CredentialAlreadyExistsException serviceException) {
        return createHttpResponse(BAD_REQUEST, serviceException.getMessage());
    }

    @ExceptionHandler(value = InvalidFormatException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> invalidFormatExceptionHandler(InvalidFormatException invalidFormatException) {
        String message = invalidFormatException.getOriginalMessage();
        return createHttpResponse(BAD_REQUEST, message);
    }

    @ExceptionHandler(value = JsonParseException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> jsonParseExceptionHandler(JsonParseException invalidFormatException) {
        String message = invalidFormatException.getOriginalMessage();
        return createHttpResponse(BAD_REQUEST, message);
    }

    @ExceptionHandler(value = VerificationCodeGeneratorCrashedException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> verificationCodeGeneratorExceptionHandler(VerificationCodeGeneratorCrashedException verificationCodeGeneratorCrashedException) {
        return createHttpResponse(BAD_REQUEST, verificationCodeGeneratorCrashedException.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    default ResponseEntity<HttpResponseDto> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrorList = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        List<String> errorsList = fieldErrorList.stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .collect(Collectors.toList());
        return createHttpResponse(BAD_REQUEST, errorsList);
    }

    default ResponseEntity<HttpResponseDto> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(HttpResponseDto.builder()
                .timeStamp(new Date())
                .httpStatus(httpStatus)
                .message(message)
                .build(), httpStatus);
    }

    default ResponseEntity<HttpResponseDto> createHttpResponse(HttpStatus httpStatus, List<String> messages) {
        return new ResponseEntity<>(HttpResponseDto.builder()
                .timeStamp(new Date())
                .httpStatus(httpStatus)
                .message(messages.get(0))
                .build(), httpStatus);
    }


}
