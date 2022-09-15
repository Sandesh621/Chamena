package com.bcis.chamena.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bcis.chamena.common.Status;
import com.bcis.chamena.databinding.ActivityLoginBinding;
import com.bcis.chamena.databinding.ActivityRegisterBinding;
import com.bcis.chamena.login.LoginActivity;
import com.bcis.chamena.model.RegisterViewModel;
import com.bcis.chamena.model.User;

public class Register extends AppCompatActivity {
    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    void register(){
        String fullName = binding.name.getText().toString();
        String phoneNumber = binding.pnum.getText().toString();
        String collegeOffice = binding.college.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.pswd.getText().toString();
        String confirmPassword = binding.cpswd.getText().toString();
        User user = new User(null,fullName,phoneNumber,collegeOffice,email,password,confirmPassword);
        RegisterViewModel viewModel = new RegisterViewModel(user);
        viewModel._status.observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if(status==Status.COMPLETED){
                    clear();
                }
            }
        });
        viewModel._message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s==null)return;
                Toast.makeText(Register.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.register();
    }
    void clear(){
        binding.name.setText("");
        binding.pnum.setText("");
        binding.college.setText("");
        binding.email.setText("");
        binding.pswd.setText("");
        binding.cpswd.setText("");
    }





}