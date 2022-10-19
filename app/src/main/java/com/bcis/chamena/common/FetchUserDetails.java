package com.bcis.chamena.common;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FetchUserDetails {
   private AddOnUserDataFetchListener listener;
    void addListener(AddOnUserDataFetchListener listener){
        this.listener=listener;
    }
    interface AddOnUserDataFetchListener{
        void onSuccess(DocumentSnapshot documentSnapshot);
        void onFailure(Exception e);
    }

    FetchUserDetails fetch(String userId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                  DocumentSnapshot snapshot=  task.getResult();
                  listener.onSuccess(snapshot);
                }else{
                    listener.onFailure(task.getException());
                }
            }
        });
        return this;

    }





}
