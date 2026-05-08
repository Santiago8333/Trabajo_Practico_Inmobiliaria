package com.in.trabajo_practico_inmobiliaria.ui.perfil;

import android.app.Application;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.in.trabajo_practico_inmobiliaria.modelo.Propietario;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> propietarioM;
    private MutableLiveData<String> mensajeError;
    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Propietario> getPropietarioM() {
        if(propietarioM == null){
            propietarioM = new MutableLiveData<>();
        }
        return propietarioM;
    }

    public MutableLiveData<String> getMensajeError() {
        if(mensajeError == null){
            mensajeError = new MutableLiveData<>();
        }
        return mensajeError;
    }

    public void cargarPerfil(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioInmobiliaria servicio = ApiClient.getServicio();
        Call<Propietario> call = servicio.obtenerPropietario(token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    Propietario miPropietario = response.body();
                    propietarioM.setValue(miPropietario);
                }else{
                    Log.e("API_ERROR", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.e("API_ERROR", "Falla de conexión: " + t.getMessage());
            }
        });



    }


    // TODO: Implement the ViewModel
}