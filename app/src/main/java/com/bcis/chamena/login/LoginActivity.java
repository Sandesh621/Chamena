package com.bcis.chamena.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bcis.chamena.databinding.ActivityLoginBinding;
import com.bcis.chamena.databinding.ActivitySplashBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}