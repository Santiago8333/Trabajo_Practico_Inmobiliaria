package com.in.trabajo_practico_inmobiliaria.ui.logout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

public class LogoutViewModel extends AndroidViewModel {
    public LogoutViewModel(@NonNull Application application) {
        super(application);
    }
    public void cerrarSesion() {

        ApiClient.crearToken(getApplication(), "");

    }
}