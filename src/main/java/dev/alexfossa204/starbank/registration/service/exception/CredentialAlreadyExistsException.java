package dev.alexfossa204.starbank.registration.service.exception;

public class CredentialAlreadyExistsException extends ServiceException {

    public CredentialAlreadyExistsException(String message) {
        super(message);
    }

    public CredentialAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
