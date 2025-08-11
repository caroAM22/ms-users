package com.pragma.plazoleta.domain.utils;

import java.util.regex.Pattern;

public class Constants {
    private Constants() {}
    public static final int MINIMUM_AGE_REQUIRED = 18;
    public static final int MAXIMUM_PHONE_LENGTH = 13;
    
    public static final String PHONE_REGEX = "^[+]?\\d{1,13}$";
    public static final String EMAIL_REGEX = "^(?!.*\\.\\.)[a-zA-Z0-9]([a-zA-Z0-9._%+-]*[a-zA-Z0-9])?@[a-zA-Z0-9]([a-zA-Z0-9.-]*[a-zA-Z0-9])?\\.[a-zA-Z]{2,}$";
    
    public static final Pattern PHONE_PATTERN_REQUIRED = Pattern.compile(PHONE_REGEX);
    public static final Pattern EMAIL_PATTERN_REQUIRED = Pattern.compile(EMAIL_REGEX);
}
