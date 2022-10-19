package com.bcis.chamena.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bcis.chamena.common.Status;
import com.bcis.chamena.services.RegisterService;
import com.google.firebase.firestore.DocumentReference;

public class RegisterViewModel extends ViewModel {
    User user;
    public RegisterViewModel(User user){
        this.user=user;
    }
    MutableLiveData<Status> status = new MutableLiveData<>(Status.IDLE);
    MutableLiveData<String> message = new MutableLiveData<>(null);
    public LiveData<Status> _status = status;
    public LiveData<String> _message = message;
    public void register(){
        if(status.getValue()!=Status.IDLE){
            return;
        }
        status.setValue(Status.PROGRESS);
        RegisterService registerService =new RegisterService(user);
        if(user.fullName.trim().isEmpty()){
            status.setValue(Status.FAILURE);
            message.setValue("Name can't be empty");
            return;
        }
        if(user.collegeOffice.isEmpty()){
            status.setValue(Status.FAILURE);
            message.setValue("College Office can't be empty");
            return;
        }
        if(!registerService.isEmail()){
            status.setValue(Status.FAILURE);
            message.setValue("Invalid Email");
            return;
        }
        if(!registerService.confirmPassword()){
            status.setValue(Status.FAILURE);
            message.setValue("Password can't match");
            return;
        }
        if(!registerService.validatePhoneNumber()){
            status.setValue(Status.FAILURE);
            message.setValue("Phone number incorrect");
            return;
        }
        registerService.register();
        registerService.addOnRegisterServiceListener(new RegisterService.RegisterServiceListener() {
            @Override
            public void onSuccess() {
                status.setValue(Status.COMPLETED);
                message.setValue("User Created Successfully");
            }

            @Override
            public void onFailure(String error) {
                status.setValue(Status.FAILURE);
                message.setValue(error);
            }
        });
    }


}
