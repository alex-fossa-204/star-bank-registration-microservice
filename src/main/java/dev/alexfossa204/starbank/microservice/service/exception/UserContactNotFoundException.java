package dev.alexfossa204.starbank.microservice.service.exception;

public class UserContactNotFoundException extends ServiceException {

    public UserContactNotFoundException(String message) {
        super(message);
    }

    public UserContactNotFoundException(Throwable cause) {
        super(cause);
    }
}
