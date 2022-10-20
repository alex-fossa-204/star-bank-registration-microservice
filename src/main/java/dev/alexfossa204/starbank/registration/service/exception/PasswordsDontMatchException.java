package dev.alexfossa204.starbank.registration.service.exception;

public class PasswordsDontMatchException extends ServiceException {

    public PasswordsDontMatchException(String message) {
        super(message);
    }

    public PasswordsDontMatchException(Throwable cause) {
        super(cause);
    }

}
