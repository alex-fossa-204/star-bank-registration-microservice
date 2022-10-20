package dev.alexfossa204.starbank.registration.service.exception;

public class VerificationCodeGeneratorCrashedException extends ServiceException {

    public VerificationCodeGeneratorCrashedException(String message) {
        super(message);
    }

    public VerificationCodeGeneratorCrashedException(Throwable cause) {
        super(cause);
    }
}
