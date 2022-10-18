package com.bcis.chamena.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.bcis.chamena.model.User;
import com.google.gson.Gson;

public class UserPref {
   public User user;
   public Context context;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    public UserPref(User user, Context context) {
        this.user = user;
        this.context = context;
         preferences= context.getSharedPreferences("user",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

   public void saveUserPref(){
        Gson gson =new Gson();
      String userJson= gson.toJson(user);
      editor.putString("data",userJson);
      editor.commit();
   }
  public User getUserPref(){
       Gson gson =new Gson();
       String data = preferences.getString("data","");
       User user = gson.fromJson(data,User.class);
       return user;
   }
   public void clearPref(){
        editor.clear().commit();
   }


}
