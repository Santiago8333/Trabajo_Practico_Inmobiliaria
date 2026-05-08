package com.in.trabajo_practico_inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.in.trabajo_practico_inmobiliaria.modelo.Propietario;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClient {
    private static final String BASE_URL = "https://capacitacion.alwaysdata.net";
//https://capacitacion.alwaysdata.net

    public static MiServicioInmobiliaria getServicio() {
        Gson gson = new GsonBuilder().setLenient().create();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        return retrofit.create(MiServicioInmobiliaria.class);
    }
    public interface MiServicioInmobiliaria{
        @FormUrlEncoded
        @POST("/api/Propietarios/login")
        Call<String> loginForm(@Field("Usuario") String usuario, @Field("Clave") String clave);

        //llama propietario
        @GET("/api/Propietarios/")
        Call<Propietario> getPropietario(@Header("Authorization") String token);

        //llama inmueble


    }
    public static void crearToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }



}
