package com.in.trabajo_practico_inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.modelo.Propietario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {
    public static final String BASE_URL = "https://capacitacion.alwaysdata.net";
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
        Call<Propietario> obtenerPropietario(@Header("Authorization") String token);

        //actualizar propietario
        @PUT("api/Propietarios/actualizar")
        Call<Propietario> putPropietario(@Header("Authorization") String token, @Body Propietario propietario);

        //actualizar clave
        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarContrasenia(@Header("Authorization") String token,
                                      @Field("currentPassword") String actual,
                                      @Field("newPassword")  String nueva);
        //resetear contraseña
        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<String> resetearContrasenia(@Header("Authorization") String token,
                                      @Field("email") String email);

        //actualizar inmueble
        // /api/Inmuebles/actualizar
        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> cambiarDisponibilidad(@Header("Authorization") String token,@Body Inmueble inmueble);








        //llama inmueble
        @GET("api/Inmuebles")
        Call<List<Inmueble>> getInmubles(@Header("Authorization") String token);




    }
    public static void crearToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", "Bearer "+token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }



}
