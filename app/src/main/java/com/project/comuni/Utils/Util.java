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

}
