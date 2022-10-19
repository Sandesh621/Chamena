package com.bcis.chamena.cart;

import android.content.Context;
import android.content.SharedPreferences;

import com.bcis.chamena.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartModel {
   public static Context context;
   public static ArrayList<Cart> carts=new ArrayList<>();
    static Gson gson = new Gson();
  public static void save(){
      SharedPreferences pref = context.getSharedPreferences("cart",Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = pref.edit();
      editor.clear();
      String cartData = gson.toJson(carts);
      editor.putString("cart",cartData);
      editor.commit();
   }
   public static void init(){
       SharedPreferences pref = context.getSharedPreferences("cart",Context.MODE_PRIVATE);
       String data = pref.getString("cart","");
       if(data.isEmpty())return;
       Type type = new TypeToken<ArrayList<Cart>>(){}.getType();
       ArrayList<Cart> user = gson.fromJson(data,type);
        carts=user;
   }

}


