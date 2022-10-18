package com.bcis.chamena.common;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bcis.chamena.model.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;

import java.util.Map;

public class FetchUserDetailsModel extends ViewModel {
    String userId;
    public FetchUserDetailsModel(String userId){
        this.userId=userId;
    }
    MutableLiveData<User> user = new MutableLiveData<>();
    public LiveData<User> _user = user;
    MutableLiveData<Status> status = new MutableLiveData<>(Status.IDLE);
    public LiveData<Status> _status = status;
   public void fetch(){
        FetchUserDetails details= new FetchUserDetails();
        details.fetch(userId).addListener(new FetchUserDetails.AddOnUserDataFetchListener() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                System.out.println(documentSnapshot.getId()+"=>"+documentSnapshot.getData());
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Exception is :"+e);
            }
        });

    }


}
