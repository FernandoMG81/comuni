package com.project.comuni.Utils;

import android.content.SharedPreferences;

import java.text.Normalizer;
import java.util.ArrayList;

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

    public static Boolean filtrarString (String TextoAFiltrar, String TextoComparacion){
        if(TextoComparacion!=null){
            if(!TextoAFiltrar.isEmpty()) {
                //Caracteres Especiales
                TextoComparacion = Normalizer.normalize(TextoComparacion, Normalizer.Form.NFD);
                TextoComparacion = TextoComparacion.replaceAll("[^\\x00-\\x7F]", "");
                TextoComparacion = TextoComparacion.toLowerCase();

                TextoAFiltrar = Normalizer.normalize(TextoAFiltrar, Normalizer.Form.NFD);
                TextoAFiltrar = TextoAFiltrar.replaceAll("[^\\x00-\\x7F]", "");
                TextoAFiltrar = TextoAFiltrar.toLowerCase();

                //Funcion en si
                if (TextoComparacion == null) {
                    return true;
                }
                if (!TextoComparacion.isEmpty()) {
                    if (TextoAFiltrar.contains(TextoComparacion)) {
                        return true;
                    }
                } else {
                    return true;
                }
                return  false;
            }
            return  false;
        }
        return  false;

    }

}
