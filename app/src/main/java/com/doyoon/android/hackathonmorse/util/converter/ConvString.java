package com.doyoon.android.hackathonmorse.util.converter;

/**
 * Created by DOYOON on 7/9/2017.
 */

public class ConvString {

    private static String COMMA = "_comma_";

    public static String comma2string(String email) {
        return email.replace(".", COMMA);
    }

    public static String string2comma(String string) {
        return string.replace(COMMA, ".");
    }
}
