package com.ecommerce.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPrefManager {

    private final SharedPreferences prefs;

    @Inject
    public SharedPrefManager(Context context) {
        prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveTokens(String accessToken, String refreshToken) {
        prefs.edit()
                .putString(Constants.KEY_ACCESS_TOKEN, accessToken)
                .putString(Constants.KEY_REFRESH_TOKEN, refreshToken)
                .apply();
    }

    public void saveUserInfo(long userId, String name, String email) {
        prefs.edit()
                .putLong(Constants.KEY_USER_ID, userId)
                .putString(Constants.KEY_USER_NAME, name)
                .putString(Constants.KEY_USER_EMAIL, email)
                .apply();
    }

    public String getAccessToken() {
        return prefs.getString(Constants.KEY_ACCESS_TOKEN, null);
    }

    public String getRefreshToken() {
        return prefs.getString(Constants.KEY_REFRESH_TOKEN, null);
    }

    public long getUserId() {
        return prefs.getLong(Constants.KEY_USER_ID, -1);
    }

    public String getUserName() {
        return prefs.getString(Constants.KEY_USER_NAME, "");
    }

    public boolean isLoggedIn() {
        return getAccessToken() != null;
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
