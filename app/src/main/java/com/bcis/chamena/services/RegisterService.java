package com.bcis.chamena.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bcis.chamena.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterService {
    User user;
    RegisterServiceListener registerServiceInterface;

    public RegisterService(User user) {
        this.user = user;
    }
  public   void addOnRegisterServiceListener(RegisterServiceListener listener){
        registerServiceInterface=listener;
    }

   public void register(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String id = authResult.getUser().getUid();
                user.id=id;
                db.collection("users").add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                registerServiceInterface.onSuccess(documentReference);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                registerServiceInterface.onFailure(e.getMessage());
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registerServiceInterface.onFailure(e.getMessage());
            }
        });
    }

   public boolean validatePhoneNumber(){
        return  user.phoneNumber.trim().length()==10;
    }
   public boolean confirmPassword(){
        return user.password.equals(user.confirmPassword);
    }
   public boolean isEmail(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
      return user.email.trim().matches(emailPattern);
    }
   public interface RegisterServiceListener{
        default void onSuccess(DocumentReference documentReference){}
        default void onFailure(String error){}
    }


}
