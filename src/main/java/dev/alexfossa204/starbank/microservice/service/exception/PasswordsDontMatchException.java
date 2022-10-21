package dev.alexfossa204.starbank.microservice.service.exception;

public class PasswordsDontMatchException extends ServiceException {

    public PasswordsDontMatchException(String message) {
        super(message);
    }

    public PasswordsDontMatchException(Throwable cause) {
        super(cause);
    }

}
