package com.ecommerce.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ecommerce.databinding.ActivityLoginBinding;
import com.ecommerce.ui.home.MainActivity;
import com.ecommerce.viewmodel.AuthViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        if (authViewModel.isLoggedIn()) {
            navigateToMain();
            return;
        }

        setupObservers();
        binding.btnLogin.setOnClickListener(v -> attemptLogin());
        binding.tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void setupObservers() {
        authViewModel.getAuthResult().observe(this, result -> {
            if (result != null) navigateToMain();
        });

        authViewModel.getError().observe(this, error -> {
            if (error != null) Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });

        authViewModel.getLoading().observe(this, loading -> {
            int visibility = loading ? View.VISIBLE : View.GONE;
            binding.loadingScrim.setVisibility(visibility);
            binding.progressBar.setVisibility(visibility);
            binding.btnLogin.setEnabled(!loading);
        });
    }

    private void attemptLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        authViewModel.login(email, password);
    }

    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
