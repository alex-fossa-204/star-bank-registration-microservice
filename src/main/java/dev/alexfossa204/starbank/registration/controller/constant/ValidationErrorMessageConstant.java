package dev.alexfossa204.starbank.registration.controller.constant;

public class ValidationErrorMessageConstant {

    public static final String PHONE_NUMBER_CHAR_VALIDATION_ERROR_MESSAGE = "The phone number must start with 375 country code and contain only 12 digits in the range from 0-9";

    public static final String PASSPORT_NUMBER_CHAR_VALIDATION_ERROR_MESSAGE = "The passport number must contain 2-30 chars, only latin chars A-Z, a-z and numerics in range 0-9";

    public static final String PASSWORD_CHAR_VALIDATION_ERROR_MESSAGE = "The password must contain 6-20 characters: latin a-z, uppercase A-Z, and at least 1 special char [ ] / ! ? @ # & $ _ * ; % ^ ( ) | < > , ~ ` . = + } ' { : -";

    public static final String SECRET_QUESTION_CHAR_VALIDATION_ERROR_MESSAGE = "Secret question must contain 1-50 chars, only latin A-Z, a-z, numerics, spaces and special chars ! ? @ # & $ _ * ; % ^ ( ) [ ] | < > , ~ ` . = + } { : / -";

    public static final String SECRET_QUESTION_ANSWER_CHAR_VALIDATION_ERROR_MESSAGE = "Secret question answer must contain 1-50 chars, only latin A-Z, a-z, spaces and special chars ! ? @ # & $ _ * ; % ^ ( ) [ ] | < > , ~ ` . = + } { : / -";

    public static final String VERIFICATION_CODE_CHAR_VALIDATION_ERROR_MESSAGE = "The verification code must contain only 6 digits in the range from 0 to 9";

    public static final String FIRST_NAME_CHAR_VALIDATION_ERROR_MESSAGE = "First name field must contain 2-30 chars, only latin A-Z a-z, numerics and special chars combination with latin A-Z, a-z";

    public static final String MIDDLE_NAME_CHAR_VALIDATION_ERROR_MESSAGE = "Middle name field must contain 0-30 chars, only latin A-Z a-z, numerics and special chars combination with latin A-Z, a-z";

    public static final String LAST_NAME_CHAR_VALIDATION_ERROR_MESSAGE = "Last name field must contain 2-30 chars, only latin A-Z a-z, numerics and special chars combination with latin A-Z, a-z";

    public static final String BOOLEAN_VALUE_CHAR_VALIDATION_ERROR_MESSAGE = "The boolean value must contain only true or false value";

}
