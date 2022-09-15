package com.bcis.chamena.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bcis.chamena.MainActivity;
import com.bcis.chamena.R;
import com.bcis.chamena.databinding.ActivitySplashBinding;
import com.bcis.chamena.register.Register;

public class SplashActivity extends AppCompatActivity {
      ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      Animation animationUtils=  AnimationUtils.loadAnimation(this, R.anim.splash_anim);
      binding.splashImage.startAnimation(animationUtils);
        // Todo:Splash Screen
      Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}