package dev.alexfossa204.starbank.microservice.service.exception;

public class UnsupportedAuthorizationMethodException extends ServiceException {

    public UnsupportedAuthorizationMethodException(String message) {
        super(message);
    }

    public UnsupportedAuthorizationMethodException(Throwable cause) {
        super(cause);
    }
}
