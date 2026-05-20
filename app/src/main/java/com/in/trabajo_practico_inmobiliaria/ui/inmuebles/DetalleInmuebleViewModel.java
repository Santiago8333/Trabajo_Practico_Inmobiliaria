package com.in.trabajo_practico_inmobiliaria.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleM = new MutableLiveData<>();
    private MutableLiveData<String> mensajeDisponibleM = new MutableLiveData<>();

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Inmueble> getInmuebleM() {
        if(inmuebleM == null){
            inmuebleM = new MutableLiveData<>();
        }
        return inmuebleM;
    }

    public MutableLiveData<String> getMensajeDisponibleM() {
        return mensajeDisponibleM;
    }

    public void cargarDetalleinmueble(Bundle bundle){
        Inmueble bundleInmueble = (Inmueble) bundle.getSerializable("inmueble",Inmueble.class);
        inmuebleM.setValue(bundleInmueble);
    }

    public void cambiarDispponibilidad(boolean disponible){
        Inmueble inmueble = inmuebleM.getValue();
        inmueble.setDisponible(disponible);
        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioInmobiliaria sv = ApiClient.getServicio();
        Call<Inmueble> call = sv.cambiarDisponibilidad(token, inmueble);
        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                try{

                    Log.d("ErrorInmueble", "codigo: " + response.code());
                    Log.d("ErrorInmueble", "mensaje: " + response.message());
                    Log.d("ErrorInmueble", "body: " + response.errorBody().string());

                }catch (Exception e){
                    Log.d("ErrorInmueble",e.toString());
                }

                if(response.isSuccessful() && response.body()!=null){
                    if(disponible){
                        mensajeDisponibleM.setValue("Inmueble Disponible");
                        Log.d("InmuebleDetalle","Inmueble Disponible");
                    }else{
                        Log.d("InmuebleDetalle","Inmueble No Disponible");
                        mensajeDisponibleM.setValue("Inmueble No Disponible");
                    }
                    inmuebleM.postValue(inmueble);

                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("ErrorInmueble",t.getMessage());
            }
        });

    }
}