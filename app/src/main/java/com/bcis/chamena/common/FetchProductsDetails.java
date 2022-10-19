package com.bcis.chamena.common;

import androidx.annotation.NonNull;

import com.bcis.chamena.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FetchProductsDetails {
    private AddOnFetchListener listener;
    interface  AddOnFetchListener{
        void onSuccess(List<Product> product);
        void onFailure(Exception e);
    }
    public void addOnFetchListener(AddOnFetchListener listener){
        this.listener=listener;
    }
    FetchProductsDetails fetchAll(){
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseFirestore.collection("products")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if(task.isSuccessful()){
                           List<Product> products=new ArrayList<>();
                        List<DocumentSnapshot> documentSnapshots= task.getResult().getDocuments();
                        for (DocumentSnapshot snapshot:documentSnapshots){
                            String addedBy = snapshot.getString("addedBy");
                            String productName = snapshot.getString("productName");
                            String category = snapshot.getString("category");
                            String imageUrl = snapshot.getString("productUrl");
                            Number price = snapshot.getDouble("price");
                            String imagePath="";
                            if(snapshot.contains("imagePath")){
                                imagePath=snapshot.getString("imagePath");
                            }
                            products.add(new Product(addedBy,productName,price,category,imageUrl,imagePath,snapshot.getId()));
                        }
                        listener.onSuccess(products);
                       }else{
                        listener.onFailure(task.getException());
                       }
                    }
                });
        return this;
    }


}
