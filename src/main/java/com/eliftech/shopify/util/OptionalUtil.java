package com.eliftech.shopify.util;

public class OptionalUtil {

    public static String getStringOrEmpty(String s) {
        return s != null ? s : "";
    }

    public static String getConcatenatedOrEmpty(String s1, String s2) {
        return s1 != null && s2 != null? s1 + " : " + s2 : "";
    }
}
