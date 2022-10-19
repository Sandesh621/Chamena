package com.bcis.chamena.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bcis.chamena.MainActivity;
import com.bcis.chamena.R;
import com.bcis.chamena.cart.CartModel;
import com.bcis.chamena.common.FetchUserDetailsModel;
import com.bcis.chamena.common.Setting;
import com.bcis.chamena.common.SettingPref;
import com.bcis.chamena.common.Status;
import com.bcis.chamena.common.UserPref;
import com.bcis.chamena.databinding.ActivitySplashBinding;
import com.bcis.chamena.model.User;
import com.bcis.chamena.register.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Set;

public class SplashActivity extends AppCompatActivity {
      ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      Animation animationUtils=  AnimationUtils.loadAnimation(this, R.anim.splash_anim);
      binding.splashImage.startAnimation(animationUtils);
        CartModel.context = getApplicationContext();
        CartModel.init();
        // Todo:Splash Screen
      Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetch(getApplicationContext());
            }
        },3000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchSetting();

    }

    public   void fetch(Context context){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            FetchUserDetailsModel detailsModel =new FetchUserDetailsModel(auth.getCurrentUser().getUid());
            detailsModel.fetch();
            detailsModel._user.observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    UserPref pref= new UserPref(user,context);
                    pref.saveUserPref();
                }
            });
            detailsModel._status.observe(this, new Observer<Status>() {
                @Override
                public void onChanged(Status status) {
                    switch (status){
                        case COMPLETED:
                        case FAILURE:
                            navigateToMainActivity(context);
                            break;
                    }
                }
            });
        }
        else{
            navigateToMainActivity(context);
        }
        return;
    }
   public void navigateToMainActivity(Context packageContext){
        Intent intent=new Intent(packageContext, MainActivity.class);
        startActivity(intent);
        finish();
    }
private void fetchSetting(){
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("settings").document("appsetting").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful()){
                DocumentSnapshot snapshot = task.getResult();
                SettingPref setting = new SettingPref(getApplicationContext());
                Setting setting1=new Setting();
                setting1.currencyCode=snapshot.getString("currency_code");
                setting.saveSetting(setting1);
            }
        }
    });
}
}