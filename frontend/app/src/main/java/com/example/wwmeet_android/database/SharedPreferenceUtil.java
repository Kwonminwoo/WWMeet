package com.example.wwmeet_android.database;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private final String SHARED_PREFERENCE_NAME = "WWMEET_SP";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putData(String key, Object object){
        editor.putString(key, ((String) object));
        editor.commit();
    }

    public void remove(String key){
        editor.remove(key);
        editor.commit();
    }

    public String getData(String key, String defaultValue){
        return sharedPreferences.getString(key, defaultValue);
    }

}
