package com.project.comuni.Utils;

import android.content.SharedPreferences;

import java.text.Normalizer;

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

    public static Boolean filtrarString (String TextoAFiltrar, String TextoCompacion){
        //Caracteres Especiales
        TextoCompacion = Normalizer.normalize(TextoCompacion, Normalizer.Form.NFD);
        TextoCompacion = TextoCompacion.replaceAll("[^\\x00-\\x7F]", "");
        TextoCompacion = TextoCompacion.toLowerCase();

        TextoAFiltrar = Normalizer.normalize(TextoAFiltrar, Normalizer.Form.NFD);
        TextoAFiltrar = TextoAFiltrar.replaceAll("[^\\x00-\\x7F]", "");
        TextoAFiltrar = TextoAFiltrar.toLowerCase();

        //Funcion en si
        if (TextoCompacion == null){
            return true;
        }
        if (!TextoCompacion.isEmpty()) {
            if (TextoAFiltrar.contains(TextoCompacion)) {
                return true;
            }
        } else {
            return true;
        }
        return  false;
    }
}
