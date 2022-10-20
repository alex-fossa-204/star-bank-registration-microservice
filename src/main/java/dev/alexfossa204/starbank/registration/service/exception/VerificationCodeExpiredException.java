package dev.alexfossa204.starbank.registration.service.exception;

public class VerificationCodeExpiredException extends ServiceException {

    public VerificationCodeExpiredException(String message) {
        super(message);
    }

    public VerificationCodeExpiredException(Throwable cause) {
        super(cause);
    }
}
