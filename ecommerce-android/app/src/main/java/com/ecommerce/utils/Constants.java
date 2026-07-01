package com.ecommerce.utils;

import android.os.Build;

import com.ecommerce.BuildConfig;

public class Constants {
    private static final String API_PORT = "8081";
    private static final String EMULATOR_HOST = "10.0.2.2";

    public static final String PREF_NAME = "ecommerce_prefs";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final int PAGE_SIZE = 20;

    public static String getBaseUrl() {
        String host = isEmulator() ? EMULATOR_HOST : BuildConfig.DEV_HOST_IP;
        return "http://" + host + ":" + API_PORT + "/";
    }

    private static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }
}
