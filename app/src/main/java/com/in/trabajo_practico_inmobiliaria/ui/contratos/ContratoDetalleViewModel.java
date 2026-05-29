package com.in.trabajo_practico_inmobiliaria.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.in.trabajo_practico_inmobiliaria.modelo.Contrato;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.modelo.Inquilino;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> inmuebleidM = new MutableLiveData<>();
    private MutableLiveData<Integer> contratoidM = new MutableLiveData<>();
    private MutableLiveData<Contrato> contratoM = new MutableLiveData<>();



    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getInmuebleidM() {
        return inmuebleidM;
    }

    public MutableLiveData<Contrato> getContratoM() {
        return contratoM;
    }

    public MutableLiveData<Integer> getContratoidM() {
        return contratoidM;
    }

    public void cargarDetalleContrato(Bundle bundle){
        Inmueble bundleInmueble = (Inmueble) bundle.getSerializable("inmuebleAlquilado",Inmueble.class);
        if (bundleInmueble != null) {
            int idObtenido = bundleInmueble.getIdInmueble();


            inmuebleidM.setValue(idObtenido);


            obtenerContratoVigente(idObtenido);
        }

    }

    public void obtenerContratoVigente(int id){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioInmobiliaria sv = ApiClient.getServicio();
        Call<Contrato> call = sv.getContratos(token,id);

        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){
                    contratoM.postValue(response.body());
                    contratoidM.setValue(response.body().getIdContrato());
                }else{
                    try{

                        Log.d("ErrorContratoDetalle", "codigo: " + response.code());
                        Log.d("ErrorContratoDetalle", "mensaje: " + response.message());
                        Log.d("ErrorContratoDetalle", "body: " + response.errorBody().string());

                    }catch (Exception e){
                        Log.d("ErrorContratoDetalle",e.toString());
                    }

                    if(response.code() == 403 || response.code() == 401){
                        Toast.makeText(getApplication(),"No se obtuvo el contrato",Toast.LENGTH_LONG).show();
                        ApiClient.crearToken(getApplication(),"");//eliminar el token si la sesion falla
                        System.exit(0);

                    }
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {

            }
        });





    }









}