package dev.alexfossa204.starbank.microservice.controller.constant;

public class RegExConstant {

    public static final String PASSPORT_NUMBER_VALIDATION_REGEX = "^[0-9A-Za-z]{2,30}$";

    public static final String SPECIAL_CHARS = "\\[\\]\\/!\\?@#\\&$_\\*;%^()|<>,~`.=\\+}\\'{:\\-\\\\";

    public static final String PASSWORD_VALIDATION_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[" + SPECIAL_CHARS + "])[0-9A-Za-z" + SPECIAL_CHARS + "]{6,20}$";

    public static final String PHONE_NUMBER_VALIDATION_REGEX = "^375\\d{9}";

    public static final String VERIFICATION_CODE_VALIDATION_REGEX = "\\d{6}";

    public static final String FIRST_NAME_VALIDATION_REGEX = "^(?=.*[A-Za-z])[A-Za-z" + SPECIAL_CHARS + "]{2,30}$";

    public static final String MIDDLE_NAME_VALIDATION_REGEX = "^[A-Za-z" + SPECIAL_CHARS + "]{0,30}$";

    public static final String LAST_NAME_VALIDATION_REGEX = "^(?=.*[A-Za-z])[A-Za-z" + SPECIAL_CHARS + "]{2,30}$";

    public static final String SECRET_QUESTION_VALIDATION_REGEX = "^[0-9A-Za-z " + SPECIAL_CHARS + "]{1,50}$";

    public static final String SECRET_QUESTION_ANSWER_VALIDATION_REGEX = "^[0-9A-Za-z " + SPECIAL_CHARS + "]{1,50}$";

    public static final String BOOLEAN_FIELD_VALIDATION_REGEX = "^(true|false)$";

}
