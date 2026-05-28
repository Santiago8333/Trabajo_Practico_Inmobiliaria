package com.in.trabajo_practico_inmobiliaria.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;

public class InquilinosDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleM = new MutableLiveData<>();

    public InquilinosDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Inmueble> getInmuebleM() {
        return inmuebleM;
    }

    public void cargarDetalleinmueble(Bundle bundle){
        Inmueble bundleInmueble = (Inmueble) bundle.getSerializable("inmuebleAlquilado",Inmueble.class);
        inmuebleM.setValue(bundleInmueble);
    }
}