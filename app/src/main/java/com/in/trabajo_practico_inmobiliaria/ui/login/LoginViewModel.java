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
/*
//saltar el login
            String tokenHardcodeado = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoibHVpc3Byb2Zlc3NvckBnbWFpbC5jb20iLCJGdWxsTmFtZSI6Ikx1aXMgUGFibGl0byBNZXJjYWRvIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiUHJvcGlldGFyaW8iLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjMiLCJleHAiOjE3NzgyNjc3MTEsImlzcyI6ImlubW9iaWxpYXJpYVVMUCIsImF1ZCI6Im1vYmlsZUFQUCJ9.o-DGwg_-fIcxEGqLtN2oqnfPcSfqBgUfDXzDtIiqBi0";
            ApiClient.crearToken(context, tokenHardcodeado);
            Log.d("Token", "Saltando login. Token usado: " + tokenHardcodeado);
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
 */
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
                        /*
                        Log.d("Error", response.message());// mensaje de erro que debvuelve
                        Log.d("Error", response.code()+"");// muestra codigo de error
                        Log.d("Error", response.errorBody().toString()+"");//trae mensaje y codigo de error
                        mensajem.setValue("Error: "+response.message());
                        */
                        // Muestra el código de error numérico (ej. 400)

                        Log.d("Error", "Código: " + response.code());

                        try {
                            // Extrae el mensaje REAL del servidor usando .string() (en minúscula y con paréntesis vacíos)
                            String errorReal = response.errorBody().string();
                            Log.d("Error", "Mensaje del Servidor: " + errorReal);

                            // Actualiza tu MutableLiveData con el mensaje real para que el usuario lo vea
                            mensajem.setValue("Error: " + errorReal);

                        } catch (Exception e) {
                            e.printStackTrace();
                            mensajem.setValue("Error desconocido al intentar iniciar sesión");
                        }
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
