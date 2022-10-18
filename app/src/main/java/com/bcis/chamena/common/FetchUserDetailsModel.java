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
                boolean isAdmin = false;
                if(documentSnapshot.contains("isAdmin")){
                    isAdmin = documentSnapshot.getBoolean("isAdmin");
                }
                String fullName = documentSnapshot.getString("fullName");
                String email = documentSnapshot.getString("email");
                String office = documentSnapshot.getString("collegeOffice");
                String phoneNumber= documentSnapshot.getString("phoneNumber");
                User currentUser =new User(userId,fullName,phoneNumber,office,email,isAdmin);
                user.postValue(currentUser);
                status.setValue(Status.COMPLETED);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Error",e.getMessage());
               status.setValue(Status.FAILURE);
            }
        });

    }


}
