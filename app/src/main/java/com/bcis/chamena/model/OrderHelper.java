package com.bcis.chamena.model;

import java.util.ArrayList;

public class OrderHelper {

   public ArrayList<Order> orders;
   public String userId;

    public OrderHelper(ArrayList<Order> orders, String userId) {
        this.orders = orders;
        this.userId = userId;
    }
}
