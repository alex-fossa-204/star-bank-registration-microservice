package dev.alexfossa204.starbank.microservice.service.exception;

public class UriSyntaxServiceException extends ServiceException {

    public UriSyntaxServiceException(String message) {
        super(message);
    }

    public UriSyntaxServiceException(Throwable cause) {
        super(cause);
    }
}
