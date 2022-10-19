package com.bcis.chamena.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Set;

public class SettingPref {

    Gson gson;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public SettingPref(Context context) {
        gson = new Gson();
        pref = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public Setting getSettingPref() {
        Gson gson = new Gson();
        String data = pref.getString("setting", "");
        Setting setting = gson.fromJson(data, Setting.class);
        return setting;
    }

    public void saveSetting(Setting setting) {
        String json = gson.toJson(setting);
        editor.putString("data", json);
        editor.commit();
    }

}
