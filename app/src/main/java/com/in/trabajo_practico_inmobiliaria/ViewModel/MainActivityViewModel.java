package com.in.trabajo_practico_inmobiliaria.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.in.trabajo_practico_inmobiliaria.modelo.Propietario;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> propietarioM = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Propietario> getPropietarioM() {
        return propietarioM;
    }

    public void getPerfil() {

        SharedPreferences preferencias = getApplication().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);


        String nombre = preferencias.getString("nombre_propietario", "Usuario");
        String email = preferencias.getString("email_propietario", "correo@ejemplo.com");


        Propietario propietarioActual = new Propietario();
        propietarioActual.setNombre(nombre);
        propietarioActual.setEmail(email);


        propietarioM.setValue(propietarioActual);
    }

    //obtener perfil
    public void ObtenerPerfil() {
        String token = ApiClient.leerToken(getApplication());

        ApiClient.MiServicioInmobiliaria sv = ApiClient.getServicio();

        Call<Propietario> call = sv.obtenerPropietario(token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Propietario propietarioApi = response.body();

                    propietarioM.postValue(propietarioApi);

                    SharedPreferences preferencias = getApplication().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString("nombre_propietario", propietarioApi.getNombre() + " " + propietarioApi.getApellido());
                    editor.putString("email_propietario", propietarioApi.getEmail());
                    editor.apply();

                } else {
                    getPerfil();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                getPerfil();
            }
        });
    }



}
