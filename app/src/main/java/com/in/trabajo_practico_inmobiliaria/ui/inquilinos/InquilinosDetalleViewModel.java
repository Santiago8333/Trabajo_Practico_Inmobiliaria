package com.in.trabajo_practico_inmobiliaria.ui.inquilinos;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> inmuebleidM = new MutableLiveData<>();
    private MutableLiveData<Inquilino> inquilinoM = new MutableLiveData<>();

    public InquilinosDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getInmuebleidM() {
        return inmuebleidM;
    }

    public MutableLiveData<Inquilino> getInquilinoM() {
        return inquilinoM;
    }

    public void cargarDetalleinquilino(Bundle bundle){
        Inmueble bundleInmueble = (Inmueble) bundle.getSerializable("inmuebleAlquilado",Inmueble.class);
        if (bundleInmueble != null) {
            int idObtenido = bundleInmueble.getIdInmueble();


            inmuebleidM.setValue(idObtenido);


            obtenerInquilinoContratoVigente(idObtenido);
        }

    }


    public void obtenerInquilinoContratoVigente(int id){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioInmobiliaria sv = ApiClient.getServicio();
        Call<Contrato> call = sv.getContratos(token,id);

        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){
                    inquilinoM.postValue(response.body().getInquilino());
                }else{
                    try{

                        Log.d("ErrorInquilinosDetalle", "codigo: " + response.code());
                        Log.d("ErrorInquilinosDetalle", "mensaje: " + response.message());
                        Log.d("ErrorInquilinosDetalle", "body: " + response.errorBody().string());

                    }catch (Exception e){
                        Log.d("ErrorInquilinosDetalle",e.toString());
                    }

                    if(response.code() == 403 || response.code() == 401){
                        Toast.makeText(getApplication(),"No se obtuvieron inquilino",Toast.LENGTH_LONG).show();
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