package com.project.comuni.Utils;

import android.content.SharedPreferences;

public class Util {

    public static String getUserMailPrefs(SharedPreferences preferences){
        return preferences.getString("email","");
    }

    //Obtiene pass guardado en preferences
    public static String getUserPassPrefs(SharedPreferences preferences){
        return preferences.getString("pass","");
    }

    public static String truncate(String value, int length){
        if (value != null && value.length() > length)
            value = value.substring(0, length);
            value = value + "...";
        return value;
    }

}
