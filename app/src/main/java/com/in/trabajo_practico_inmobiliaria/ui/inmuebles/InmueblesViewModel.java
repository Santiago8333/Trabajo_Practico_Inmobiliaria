package com.in.trabajo_practico_inmobiliaria.ui.inmuebles;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {


    private MutableLiveData<Inmueble> inmuebleM;

    private MutableLiveData<List<Inmueble>> listaInmuebles = new MutableLiveData<>();


    public InmueblesViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Inmueble>> getListaInmuebles() {
        return listaInmuebles;
    }

    public MutableLiveData<Inmueble> getInmuebleM() {
        return inmuebleM;
    }

    public void obtenerListaInmuebles(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioInmobiliaria sv = ApiClient.getServicio();
        Call<List<Inmueble>> call = sv.getInmubles(token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    listaInmuebles.postValue(response.body());
                }else{
                    try{

                        Log.d("ErrorInmueble", "codigo: " + response.code());
                        Log.d("ErrorInmueble", "mensaje: " + response.message());
                        Log.d("ErrorInmueble", "body: " + response.errorBody().string());

                    }catch (Exception e){
                        Log.d("ErrorInmueble",e.toString());
                    }

                    if(response.code() == 403 || response.code() == 401){
                        Toast.makeText(getApplication(),"No se obtuvieron Inmuebles",Toast.LENGTH_LONG).show();
                        ApiClient.crearToken(getApplication(),"");//eliminar el token si la sesion falla
                        System.exit(0);


                    }

                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("ErrorInmueble",t.getMessage());

            }
        });
    }





}