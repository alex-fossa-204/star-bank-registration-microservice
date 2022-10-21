package dev.alexfossa204.starbank.microservice.service.exception;

public class CredentialAlreadyExistsException extends ServiceException {

    public CredentialAlreadyExistsException(String message) {
        super(message);
    }

    public CredentialAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
