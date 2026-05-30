package com.in.trabajo_practico_inmobiliaria.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.modelo.Pago;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> listaPagos = new MutableLiveData<>();

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Pago>> getListaPagos() {
        return listaPagos;
    }

    public void cargarPagos(Bundle bundle){
        if (bundle != null && bundle.containsKey("idContrato")) {

            int idContratoObtenido = bundle.getInt("idContrato");


            obtenerListaPagos(idContratoObtenido);
            Log.d("Pagos", "El ID del contrato recibido es: " + idContratoObtenido);

        }

    }

    public void obtenerListaPagos(int id){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioInmobiliaria sv = ApiClient.getServicio();
        Call<List<Pago>> call = sv.getPagos(token,id);

        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if(response.isSuccessful()){
                    listaPagos.postValue(response.body());
                }else{
                    try{

                        Log.d("ErrorPago", "codigo: " + response.code());
                        Log.d("ErrorPago", "mensaje: " + response.message());
                        Log.d("ErrorPago", "body: " + response.errorBody().string());

                    }catch (Exception e){
                        Log.d("ErrorPago",e.toString());
                    }

                    if(response.code() == 403 || response.code() == 401){
                        Toast.makeText(getApplication(),"No se obtuvieron Pago",Toast.LENGTH_LONG).show();
                        ApiClient.crearToken(getApplication(),"");//eliminar el token si la sesion falla
                        System.exit(0);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("ErrorPago",t.getMessage());
            }
        });


    }

}