package com.pasich.mynotes.utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class Base64ServiceHelper {

    public static String encodeString(String string) {
        return Base64.encodeToString(string.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
    }

    public static String decodeString(String string) {
        return new String(Base64.decode(string, Base64.DEFAULT), StandardCharsets.UTF_8);
    }
}
