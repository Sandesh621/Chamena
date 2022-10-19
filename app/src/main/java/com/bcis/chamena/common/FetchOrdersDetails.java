package com.bcis.chamena.common;

import androidx.annotation.NonNull;

import com.bcis.chamena.model.Order;
import com.bcis.chamena.model.OrderHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FetchOrdersDetails {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FetchOrderListener listener;
 public    interface FetchOrderListener{
        void onFetchSuccess(List<OrderHelper> orders);
        void onFetchFailure(Exception e);
    }
    public void OnAddFetchOrderListener(FetchOrderListener listener){
     this.listener=listener;
    }


  private   void groupingOrders(List<Order> orders){
        ArrayList<String> userIds= new ArrayList<>();
        for (Order order:orders){
            if(!userIds.contains(order.orderBy)){
                userIds.add(order.orderBy);
            }
        }
    List<OrderHelper> orderHelpers= new ArrayList<>();
      for (String userId:userIds){
          ArrayList<Order> userOrder = new ArrayList<>();
          for (Order order:orders){
                if(userId.equals(order.orderBy)){
                    userOrder.add(order);
                }
          }
          orderHelpers.add(new OrderHelper(userOrder,userId));

      }

      listener.onFetchSuccess(orderHelpers);


    }

   public void fetchOrders(){
        db.collection("orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                 List<DocumentSnapshot> documents= task.getResult().getDocuments();
                 List<Order> orders= new ArrayList<>();
                 for (DocumentSnapshot snapshot:documents){
                     Order order = new Order(snapshot.getString("orderBy"),snapshot.getDouble("orderItems"),snapshot.getDouble("price"),
                             snapshot.getString("productId"),snapshot.getDouble("timestamp"),
                             snapshot.getString("productName"),snapshot.getString("orderByName"),
                             snapshot.getString("url"));
                     orders.add(order);
                 }
                 groupingOrders(orders);
                }else{
                    listener.onFetchFailure(task.getException());
                }
            }
        });
    }


}
