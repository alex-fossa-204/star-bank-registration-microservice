package dev.alexfossa204.starbank.microservice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientRegistrationConstant {

    LONG_ID_DUMMY("10"),

    REGISTRATION_NON_CLIENT_URL("/a-banking/registration-management/registration/non-client"),
    REGISTRATION_CLIENT_URL("/a-banking/registration-management/registration/client"),

    REGISTRATION_CLIENT_VALIDATION_URL("/a-banking/registration-management/verification/credentials/phone-number"),

    REGISTRATION_SEND_REQUEST_FOR_CODE_GENERATION_URL("/a-banking/registration-management/user/verification-code/new-code"),

    LASTNAME_CORRECT_CHARS_DUMMY("Smith"),
    LASTNAME_INCORRECT_CHARS_DUMMY("Смит"),
    LASTNAME_EMPTY_DUMMY(""),

    FIRSTNAME_CORRECT_CHARS_DUMMY("Alex"),
    FIRSTNAME_INCORRECT_CHARS_DUMMY("Алекс"),
    FIRSTNAME_EMPTY_DUMMY(""),

    MIDDLE_NAME_CORRECT_CHARS_DUMMY("David"),
    MIDDLE_NAME_INCORRECT_CHARS_DUMMY("Дэвид"),
    MIDDLE_NAME_EMPTY_DUMMY(""),

    PHONE_CORRECT_CHARS_DUMMY("375331001010"),
    PHONE_INCORRECT_CHARS_DUMMY("375UP83475"),
    PHONE_EMPTY_DUMMY(""),

    PASSPORT_NUMBER_CORRECT_CHARS_DUMMY("2341254E448PB1"),
    PASSPORT_NUMBER_INCORRECT_CHARS_DUMMY("ФЫва254E448PB1"),
    PASSPORT_NUMBER_EMPTY_DUMMY(""),

    PASSPORT_IS_US_RESIDENT_TRUE("true"),
    PASSPORT_IS_US_RESIDENT_FALSE("false"),

    EMAIL_EMPTY_DUMMY("EMAIL_DUMMY"),
    SKYPE_EMPTY_DUMMY("SKYPE_DUMMY"),
    USER_UID_CORRECT_DUMMY("2935872952383"),
    USER_ROLE_NAME_DUMMY("DUMMY_ROLE"),
    IMAGE_URL_DUMMY("DUMMY_IMAGE_URL"),
    IS_USER_ACTIVE_TRUE_DUMMY("true"),
    IS_USER_ACTIVE_FALSE_DUMMY("false"),

    IS_CLIENT_TRUE_DUMMY("true"),
    IS_CLIENT_FALSE_DUMMY("false"),

    IS_CREDENTIAL_NON_EXPIRED_TRUE("true"),
    IS_CREDENTIAL_NON_EXPIRED_FALSE("false"),

    PASSWORD_CORRECT_CHARS_V1_DUMMY("123456Customer$1"),
    PASSWORD_CORRECT_CHARS_V2_DUMMY("123456Employee$1"),
    PASSWORD_INCORRECT_CHARS_DUMMY("312Фыва$Afdg"),
    PASSWORD_EMPTY_DUMMY(""),

    SECRET_QUESTION_CORRECT_CHARS_DUMMY("Mothers maiden lastname?"),
    SECRET_QUESTION_INCORRECT_CHARS_DUMMY("Девичья фамилия матери?"),
    SECRET_QUESTION_EMPTY_DUMMY(""),

    SECRET_QUESTION_ANSWER_CORRECT_CHARS_DUMMY("Johnson"),
    SECRET_QUESTION_ANSWER_INCORRECT_CHARS_DUMMY("Джонсон"),
    SECRET_QUESTION_ANSWER_EMPTY_DUMMY(""),

    VERIFICATION_CODE_CORRECT_CHARS_DUMMY("123456"),
    VERIFICATION_CODE_INCORRECT_CHARS_DUMMY("12345R"),
    VERIFICATION_CODE_EMPTY_DUMMY("");

    private String dummy;

}
