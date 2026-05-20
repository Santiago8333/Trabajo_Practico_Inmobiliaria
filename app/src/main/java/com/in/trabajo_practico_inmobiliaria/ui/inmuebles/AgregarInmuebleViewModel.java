package com.in.trabajo_practico_inmobiliaria.ui.inmuebles;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgregarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> imagenSeleccionadaM = new MutableLiveData<>();


    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Uri> getImagenSeleccionadaM() {
        return imagenSeleccionadaM;
    }
    public void setImagen(Uri uri){
        imagenSeleccionadaM.setValue(uri);
    }
}