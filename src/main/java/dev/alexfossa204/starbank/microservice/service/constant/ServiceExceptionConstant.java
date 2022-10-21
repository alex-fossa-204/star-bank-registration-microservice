package dev.alexfossa204.starbank.microservice.service.constant;

public class ServiceExceptionConstant {

    public static final String CLIENT_PRESENT_BUT_HAS_CREDENTIALS_REGISTRATION_DENIED = "Client already exists and registered. Try to recover your credentials or contact the administration";

    public static final String CLIENT_PRESENT_BUT_DONT_HAS_CREDENTIALS_REGISTRATION_ACCEPTED = "Client exists but not registered. Registration Accepted";

    public static final String CLIENT_NOT_PRESENT_REGISTRATION_ACCEPTED = "Client does not exists. Registration Accepted";

    public static final String ROLE_NOT_PRESENT_REGISTRATION_DENIED = "Role not found. Registration denied. roleName = %s";

    public static final String PASSWORDS_DONT_MATCH_MSG = "Passwords dont match. Try again";

    public static final String VERIFICATION_CODE_NOT_FOUND_MSG = "Verification code not found";

    public static final String VERIFICATION_CODE_ALREADY_USED_OR_UNAVAILABLE = "Verification Code was already used or unavailable any more";

    public static final String USER_CONTACT_NOT_FOUND_MSG = "There is no any matches. Contact: %S";

    public static final String STATUS_CODE_METHOD_NOT_ALLOWED = "This request method is not allowed on this endpoint";

    public static final String METHOD_NOT_SUPPORTED = "for this URL";

    public static final String STATUS_CODE_NOT_FOUND_CODE_URL_HANDLER_NOT_EXISTS = "There is no mapping for this URL";

    public static final String STATUS_CODE_BAD_REQUEST_INCORRECT_CREDENTIALS = "Incorrect credentials";

    public static final String STATUS_CODE_BAD_REQUEST_VERIFICATION_CODE_CAN_NOT_BE_GENERATED = "Verification Code can not be generated. Contact the administration";

    public static final String STATUS_CODE_OK_NEW_USER_REGISTERED_SUCCESSFULLY = "User successfully registered! Phone login: %s, Passport login: %s";

    public static final String STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_CLIENT = "Dear Client! Verification code was sent on your phone";

    public static final String STATUS_CODE_OK_VERIFICATION_CODE_SENT_AS_MESSAGE_FOR_NON_CLIENT = "Dear Non Client! Verification code was sent on your phone";

    public static final String NON_CLIENT_REGISTRATION_ATTEMPT = "Non Client Registration Attempt";

    public static final String CLIENT_REGISTRATION_ATTEMPT = "Client Registration Attempt";
}
