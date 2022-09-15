package com.bcis.chamena.services;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginService {
    public LoginServiceInterfaceListener loginServiceInterfaceListener;
    String email;
    String password;

    public void OnLoginServiceInterfaceListener(LoginServiceInterfaceListener listener){
        loginServiceInterfaceListener=listener;
    }
    public LoginService(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public  void login(){
        FirebaseAuth auth =  FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        loginServiceInterfaceListener.onSuccess(authResult);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginServiceInterfaceListener.onFailure(e.getMessage());
                    }
                });
    }



    public  interface  LoginServiceInterfaceListener{
        default void onSuccess(AuthResult result){
        }
        default void onFailure(String error){}

    }



}
