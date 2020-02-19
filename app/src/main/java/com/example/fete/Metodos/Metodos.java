package com.example.fete.Metodos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.widget.ImageView;


import com.example.fete.R;
import com.squareup.picasso.Picasso;

public class Metodos {

    public void imagePicasso(Context context, String pathImage, ImageView image){
        Picasso.with(context)
                .load(pathImage)
                .placeholder(R.drawable.ic_camera_black_24dp)
                .centerCrop()
                .resize(400,400)
                .into(image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() { }
                    @Override
                    public void onError() { }});
    }

    public static boolean isGPSEnabled(Context context) {
        try {
            int gpsSignal = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            return gpsSignal == 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void showInfoAlert(final Context context, String titulo, String mensaje) {
        new AlertDialog.Builder(context).setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}

