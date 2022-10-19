package com.bcis.chamena.common;

import androidx.annotation.NonNull;

import com.bcis.chamena.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ManipulateProductDetails {
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;
    private OnProductDeleteListener onProductDeleteListener;
    private OnProductUpdateListener onProductUpdateListener;
    public ManipulateProductDetails(){
        db= FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
        storageReference= storage.getReference();
    }
interface OnProductUpdateListener{
        void onSuccess();
        void onFailure(Exception e);
}
public interface OnProductDeleteListener{
        void onSuccess();
        void onFailure(Exception e);
}
public void addOnProductUpdateListener(OnProductUpdateListener listener){
        onProductUpdateListener=listener;
}
public void addOnProductDeleteListener(OnProductDeleteListener listener){
        onProductDeleteListener=listener;
}
public void update(String docId, Product product){
        db.collection("product").document(docId).set(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            onProductUpdateListener.onSuccess();
                        }else{
                            onProductUpdateListener.onFailure(task.getException());
                        }
                    }
                });
}

public void delete(String docId,String path){
        if(docId.isEmpty())return;
        db.collection("products").document(docId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(!path.isEmpty()){
                    storageReference.child(path).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                onProductDeleteListener.onSuccess();
                            }else{
                                onProductDeleteListener.onFailure(task.getException());
                            }
                        }
                    });
                    }else{
                        onProductDeleteListener.onSuccess();
                    }
                }else{
                    onProductDeleteListener.onFailure(task.getException());
                }

            }
        });
    }


}
