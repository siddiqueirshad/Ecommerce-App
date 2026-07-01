package com.ecommerce.repository;

import com.ecommerce.model.*;
import com.ecommerce.network.ApiService;
import com.ecommerce.utils.SharedPrefManager;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class AuthRepository {

    private final ApiService apiService;
    private final SharedPrefManager prefManager;

    @Inject
    public AuthRepository(ApiService apiService, SharedPrefManager prefManager) {
        this.apiService = apiService;
        this.prefManager = prefManager;
    }

    public AuthResponse login(String email, String password) throws IOException {
        Response<ApiResponse<AuthResponse>> response =
                apiService.login(new LoginRequest(email, password)).execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            AuthResponse auth = response.body().getData();
            prefManager.saveTokens(auth.getAccessToken(), auth.getRefreshToken());
            prefManager.saveUserInfo(auth.getUserId(), auth.getName(), auth.getEmail());
            return auth;
        }
        throw new IOException(getErrorMessage(response));
    }

    public AuthResponse register(String name, String email, String password) throws IOException {
        Response<ApiResponse<AuthResponse>> response =
                apiService.register(new RegisterRequest(name, email, password)).execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            AuthResponse auth = response.body().getData();
            prefManager.saveTokens(auth.getAccessToken(), auth.getRefreshToken());
            prefManager.saveUserInfo(auth.getUserId(), auth.getName(), auth.getEmail());
            return auth;
        }
        throw new IOException(getErrorMessage(response));
    }

    public void logout() {
        prefManager.clear();
    }

    public boolean isLoggedIn() {
        return prefManager.isLoggedIn();
    }

    private String getErrorMessage(Response<?> response) {
        if (response.body() instanceof ApiResponse) {
            return ((ApiResponse<?>) response.body()).getMessage();
        }
        return "Request failed: " + response.code();
    }
}
