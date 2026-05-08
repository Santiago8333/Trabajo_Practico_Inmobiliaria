package com.in.trabajo_practico_inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.in.trabajo_practico_inmobiliaria.MainActivity;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    MutableLiveData<String> mensajem = new MutableLiveData<>();
    private Context context;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }
    public LiveData<String> getMensaje(){
        return mensajem;
    }
    public void recuperarDatos(String email,String clave){
        if(email.isEmpty() || clave.isEmpty()){
            mensajem.setValue("Por favor, complete todos los campos");

        }else {
            //Implementar la interface
            ApiClient.MiServicioInmobiliaria servicioInmobiliaria = ApiClient.getServicio();
            Call<String> call = servicioInmobiliaria.loginForm(email, clave);
            call.enqueue(new Callback<String>(){
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        String token = response.body();
                        ApiClient.crearToken(context,token);
                        Log.d("Token",token);
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else{
                        Log.d("Error", response.message());// mensaje de erro que debvuelve
                        Log.d("Error", response.code()+"");// muestra codigo de error
                        Log.d("Error", response.errorBody().toString()+"");//trae mensaje y codigo de error
                        mensajem.setValue("Error: "+response.message());
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("mensaje",t.getMessage());
                    mensajem.setValue("Error: "+t.getMessage());
                }
            });
        }
    }




}
