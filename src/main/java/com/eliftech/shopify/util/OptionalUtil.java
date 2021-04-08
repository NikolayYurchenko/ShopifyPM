package com.eliftech.shopify.util;

public class OptionalUtil {

    public static String getStringOrEmpty(String s) {
        return s != null ? s : "";
    }

    public static String getConcatenatedOrEmpty(String s1, String s2) {
        return s1 != null && s2 != null? s1 + " : " + s2 : "";
    }

    public static String formatName(String firstName, String lastName) {

        String firstNameUpper = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        String lastNameUpper = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        return firstNameUpper + "-" + lastNameUpper;
    }
}
