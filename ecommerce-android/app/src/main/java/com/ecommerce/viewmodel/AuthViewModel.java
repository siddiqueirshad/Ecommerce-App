package com.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ecommerce.model.AuthResponse;
import com.ecommerce.repository.AuthRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<AuthResponse> authResult = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    @Inject
    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<AuthResponse> getAuthResult() { return authResult; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getLoading() { return loading; }

    public void login(String email, String password) {
        loading.setValue(true);
        executor.execute(() -> {
            try {
                AuthResponse result = authRepository.login(email, password);
                authResult.postValue(result);
            } catch (Exception e) {
                error.postValue(e.getMessage());
            } finally {
                loading.postValue(false);
            }
        });
    }

    public void register(String name, String email, String password) {
        loading.setValue(true);
        executor.execute(() -> {
            try {
                AuthResponse result = authRepository.register(name, email, password);
                authResult.postValue(result);
            } catch (Exception e) {
                error.postValue(e.getMessage());
            } finally {
                loading.postValue(false);
            }
        });
    }

    public boolean isLoggedIn() {
        return authRepository.isLoggedIn();
    }

    public void logout() {
        authRepository.logout();
    }
}
