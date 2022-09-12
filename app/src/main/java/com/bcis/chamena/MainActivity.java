package com.bcis.chamena;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.bcis.chamena.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    final int  SPLASH_TIME=1500;
    ActivityMainBinding binding;
     EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





    }
}