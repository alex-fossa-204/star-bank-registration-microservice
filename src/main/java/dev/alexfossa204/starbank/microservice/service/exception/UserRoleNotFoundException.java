package dev.alexfossa204.starbank.microservice.service.exception;

public class UserRoleNotFoundException extends ServiceException {

    public UserRoleNotFoundException(String message) {
        super(message);
    }

    public UserRoleNotFoundException(Throwable cause) {
        super(cause);
    }
}
