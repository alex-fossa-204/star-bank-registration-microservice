package dev.alexfossa204.starbank.microservice.service.exception;

public class VerificationCodeGeneratorCrashedException extends ServiceException {

    public VerificationCodeGeneratorCrashedException(String message) {
        super(message);
    }

    public VerificationCodeGeneratorCrashedException(Throwable cause) {
        super(cause);
    }
}
