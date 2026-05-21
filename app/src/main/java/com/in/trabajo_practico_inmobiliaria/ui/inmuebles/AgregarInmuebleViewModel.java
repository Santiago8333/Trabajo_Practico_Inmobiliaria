package com.in.trabajo_practico_inmobiliaria.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.modelo.Propietario;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> imagenSeleccionadaM = new MutableLiveData<>();
    private MutableLiveData<String> mensajeM = new MutableLiveData<>();


    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Uri> getImagenSeleccionadaM() {
        return imagenSeleccionadaM;
    }


    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            imagenSeleccionadaM.setValue(uri);
        }
    }

    public void cargarInmueble(String direccion,String uso,String tipo,String ambientes,String superficie,String valor){

        try{
            if(direccion.isEmpty()|| uso.isEmpty() || tipo.isEmpty() || ambientes.isEmpty() || superficie.isEmpty() || valor.isEmpty()) {
                Toast.makeText(getApplication(), "Datos faltantes", Toast.LENGTH_SHORT).show();

            }else{
                Inmueble i = new Inmueble();
                i.setDireccion(direccion);
                i.setUso(uso);
                i.setTipo(tipo);
                i.setAmbientes(Integer.parseInt(ambientes));
                i.setSuperficie(Integer.parseInt(superficie));
                i.setValor(Double.parseDouble(valor));

                byte[] imagen = transformarImagen();
                if(imagen.length==0){
                    Toast.makeText(getApplication(), "Debe ingresar imagen", Toast.LENGTH_LONG).show();
                    return;
                }

                String inmuebleJson = new Gson().toJson(i);
                RequestBody inmuebleBody = RequestBody.create(
                        inmuebleJson,
                        MediaType.parse("application/json; charset=utf-8")
                );
                RequestBody requestFile = RequestBody.create(
                        imagen,
                        MediaType.parse("image/jpeg")
                );
                //RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
                //RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
                MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);
                String token = ApiClient.leerToken(getApplication());
                ApiClient.MiServicioInmobiliaria sv = ApiClient.getServicio();
                Call<Inmueble> call = sv.CargarInmueble(token,imagenPart,inmuebleBody);
                call.enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplication(), "Inmueble guardado correctamente", Toast.LENGTH_SHORT).show();
                            Log.d("ErrorInmueble","cargado");
                        }else{
                            Toast.makeText(getApplication(), "Error al cargar inmueble", Toast.LENGTH_SHORT).show();
                            try{

                                Log.d("ErrorInmueble", "codigo: " + response.code());
                                Log.d("ErrorInmueble", "mensaje: " + response.message());
                                Log.d("ErrorInmueble", "body: " + response.errorBody().string());

                            }catch (Exception e){
                                Log.d("ErrorInmueble",e.toString());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable t) {
                        Log.d("ErrorInmueble",t.getMessage());
                    }
                });



            }



        }catch (NumberFormatException e){

        }









    }

    //pasar imagen a bytes
    private byte[] transformarImagen() {
        try {
            Uri uri = imagenSeleccionadaM.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException ex) {
            Toast.makeText(getApplication(), "Debe ingresar una foto", Toast.LENGTH_LONG).show();
            return new byte[]{};
        }

    }
}