package com.example.wwmeet_android;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wwmeet_android.domain.Appointment;

import java.util.List;
import java.util.Set;

public class SharedPreferenceUtil {
    private static final String NAME = "WWMeet_token";
    private static SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    public SharedPreferenceUtil(Context context){
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putData(String key, Object object) {
        editor.putString(key, ((String) object));
        editor.commit();
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public String getData(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public Set<String> getData(String key, Set<String> defaultValue){
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    public void putData(String key, Set<String> data) {
        editor.putStringSet(key, data);
        editor.commit();
    }
}
