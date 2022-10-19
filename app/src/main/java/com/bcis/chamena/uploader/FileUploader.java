package com.bcis.chamena.uploader;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import javax.xml.transform.Result;

public class FileUploader {

    FileUploaderInterface uploaderInterface;
     public interface  FileUploaderInterface {
        void onSuccess(String url);
        void  onFailure(Exception e);
    }

    public void addOnFileUploaderListener(FileUploaderInterface fileUploaderInterface){
         uploaderInterface=fileUploaderInterface;
    }
    public void upload(Uri uri){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        String filename = new File(uri.getPath().toString()).getName();
       storageReference= storageReference.child("foodsImages/"+filename);
        UploadTask uploadTask = (UploadTask) storageReference.putFile(uri);
        StorageReference finalStorageReference = storageReference;
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return finalStorageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    uploaderInterface.onSuccess(downloadUri.toString());
                }else{
                    uploaderInterface.onFailure(task.getException());
                }
            }
        });
        return;
    }

}
