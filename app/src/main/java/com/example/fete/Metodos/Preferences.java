package com.example.fete.Metodos;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String STRING_PREFERENCES = "MD.FETE";
    public static final String BUTTON_MENSAJE_CONFIG_MAPA ="ESTADO.BUTTON.CONFIG";
    public static final String BUTTON_MENSAJE_CONFIG ="BUTTON.MENSAJE.CONFIG";
    public static final String NOMBRE_BUSQ_USUARIO = "NOMBRE.USUARIO.BUSQUEDA";
    public static final String MENSAJES_CUMPLEANIOS ="MENSAJE.CUMPLEANIOS";
    public static final String MINUTOS_NOTIFICACION ="MINUTOS.NOTIFICACIONES.CLIENTE";
    public static final String MENSAJE_CLIENTE = "MENSAJE.CLIENTE";

    public static void savePreferenceBoolean(Context c, boolean b, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key,b).apply();
    }

    public static void savePreferenceString(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }


    public static Boolean obtenerPreferenceBoolean(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean(key,false);
    }

    public static String  ObtenerPreferenceString(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(key," ");
    }
}