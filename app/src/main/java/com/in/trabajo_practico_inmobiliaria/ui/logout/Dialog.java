package com.in.trabajo_practico_inmobiliaria.ui.logout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class Dialog {






    public void mostrarDialog(Activity activity){

        new AlertDialog.Builder(activity)
                .setTitle("Bye Bye")
                .setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.finish();
                        // activity.finishAndRemoveTask();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // IMPORTANTE: Faltaba .show() al final del Toast
                        Toast.makeText(activity, "Seguimos aquí", Toast.LENGTH_LONG).show();
                    }
                }).show(); // IMPORTANTE: Faltaba .show() al final del Builder para mostrar la alerta
    }





}




