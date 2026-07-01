package com.ecommerce.model;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("refreshToken")
    private String refreshToken;
    @SerializedName("tokenType")
    private String tokenType;
    @SerializedName("userId")
    private long userId;
    private String name;
    private String email;
    private String role;

    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public String getTokenType() { return tokenType; }
    public long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
