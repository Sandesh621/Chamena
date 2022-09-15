package com.bcis.chamena.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bcis.chamena.common.Status;
import com.bcis.chamena.services.LoginService;
import com.google.firebase.auth.AuthResult;

public class LoginViewModel  extends ViewModel {
    MutableLiveData<Status> status = new MutableLiveData<>(Status.IDLE);
    MutableLiveData<String> message = new MutableLiveData<>(null);
    public LiveData<Status> _status = status;
    public LiveData<String> _message = message;
   public void login(String email,String password){
        if(email.trim().isEmpty()){
            status.setValue(Status.FAILURE);
            message.setValue("Email Field can't be empty");
            return;
        }
        if(password.trim().isEmpty()){
            status.setValue(Status.FAILURE);
            message.setValue("Password can't be empty");
            return;
        }
       LoginService loginService = new LoginService(email,password);
        loginService.login();
        loginService.OnLoginServiceInterfaceListener(new LoginService.LoginServiceInterfaceListener() {
            @Override
            public void onSuccess(AuthResult result) {
                status.setValue(Status.COMPLETED);
                message.setValue("Login Successfully");
            }

            @Override
            public void onFailure(String error) {
                status.setValue(Status.FAILURE);
                message.setValue(error);
            }
        });



    }

}
