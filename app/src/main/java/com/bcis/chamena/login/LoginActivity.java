package com.bcis.chamena.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bcis.chamena.MainActivity;
import com.bcis.chamena.common.Status;
import com.bcis.chamena.databinding.ActivityLoginBinding;
import com.bcis.chamena.databinding.ActivitySplashBinding;
import com.bcis.chamena.model.LoginViewModel;
import com.bcis.chamena.register.Register;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }
    void login(){
        String email = binding.email.getText().toString();
        String password = binding.pswd.getText().toString();
        LoginViewModel loginViewModel = new LoginViewModel();
        loginViewModel._status.observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if(status==Status.COMPLETED){
                    Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
        loginViewModel._message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s==null)return;
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        loginViewModel.login(email,password);
    }


}