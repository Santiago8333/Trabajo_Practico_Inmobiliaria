package com.in.trabajo_practico_inmobiliaria.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.util.Patterns;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.in.trabajo_practico_inmobiliaria.modelo.Propietario;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> propietarioM;
    private MutableLiveData<String> mensajePerfilM;
    private MutableLiveData<String> mensajeClaveM;
    private MutableLiveData<Boolean> botonM;
    private MutableLiveData<String> botonMensajeM;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Propietario> getPropietarioM() {
        if (propietarioM == null) {
            propietarioM = new MutableLiveData<>();
        }
        return propietarioM;
    }

    public MutableLiveData<String> getMensajePerfilM() {
        if (mensajePerfilM == null) {
            mensajePerfilM = new MutableLiveData<>();
        }
        return mensajePerfilM;
    }

    public MutableLiveData<String> getMensajeClaveM() {
        if (mensajeClaveM == null) {
            mensajeClaveM = new MutableLiveData<>();
        }
        return mensajeClaveM;
    }

    public MutableLiveData<Boolean> getBoton() {
        if (botonM == null) {
            botonM = new MutableLiveData<>();
        }
        return botonM;
    }

    public MutableLiveData<String> getBotonMensajeM() {
        if (botonMensajeM == null) {
            botonMensajeM = new MutableLiveData<>();
        }
        return botonMensajeM;
    }

    public void cargarPerfil() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioInmobiliaria servicio = ApiClient.getServicio();
        Call<Propietario> call = servicio.obtenerPropietario(token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    Propietario miPropietario = response.body();
                    propietarioM.setValue(miPropietario);
                } else {
                    Log.e("API_ERROR", "Error en la respuesta: " + response.code());
                    mensajePerfilM.setValue("API_ERROR Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.e("API_ERROR", "Falla de conexión: " + t.getMessage());
                mensajePerfilM.setValue("API_ERROR Error en la respuesta: " + t.getMessage());
            }
        });


    }
/*
    public void CambiarEstadoBoton(){
        // Usamos getBoton() y getBotonMensajeM() para asegurar que no sean null
        Boolean estadoActual = getBoton().getValue();

        if (estadoActual != null && estadoActual == true) {
            getBoton().setValue(false);
            getBotonMensajeM().setValue("Editar");
        } else {
            getBoton().setValue(true);
            getBotonMensajeM().setValue("Guardar");
        }
    }

 */


    public void procesarAccionBoton(String dni, String nombre, String apellido, String email, String telefono) {
        Boolean estadoActual = getBoton().getValue();

        if (estadoActual != null && estadoActual == true) {

            Propietario propietarioEditado = new Propietario();

            propietarioEditado.setDni(dni);
            propietarioEditado.setNombre(nombre);
            propietarioEditado.setApellido(apellido);
            propietarioEditado.setEmail(email);
            propietarioEditado.setTelefono(telefono);

            //Log.d("pro",propietarioEditado.getDni());

            guardarPerfil(propietarioEditado);
            //Log.d("p",propietarioEditado.getDni());


            getBoton().setValue(false);
            getBotonMensajeM().setValue("Editar");

        } else {
            getBoton().setValue(true);
            getBotonMensajeM().setValue("Guardar");
        }
    }

    //actualizamos perfil
    private void guardarPerfil(Propietario propietarioEditado) {
        if (propietarioEditado.getDni().isEmpty() || propietarioEditado.getNombre().isEmpty()
                || propietarioEditado.getApellido().isEmpty()
                || propietarioEditado.getEmail().isEmpty()
                || propietarioEditado.getTelefono().isEmpty()) {

            getMensajePerfilM().setValue("Por favor, complete todos los campos");

        } else {
            // 2. Validar formato del Email
            if (!Patterns.EMAIL_ADDRESS.matcher(propietarioEditado.getEmail()).matches()) {
                getMensajePerfilM().setValue("Por favor, ingrese un email válido");
                return;
            }

            // 3. Validar formato del Teléfono
            if (!Patterns.PHONE.matcher(propietarioEditado.getTelefono()).matches()) {
                getMensajePerfilM().setValue("Por favor, ingrese un número de teléfono válido");
                return;
            }


            String token = ApiClient.leerToken(getApplication());
            ApiClient.MiServicioInmobiliaria sv = ApiClient.getServicio();
            Call<Propietario> call = sv.putPropietario(token, propietarioEditado);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        Propietario nuevo = response.body();
                        propietarioM.setValue(nuevo);
                        mensajePerfilM.setValue("Datos Cargados");
                    } else {
                        try {
                            String errorReal = response.errorBody().string();
                            Log.d("error", "codigo: " + response.code());
                            Log.d("error", "mennsaje: " + response.message());
                            Log.d("error", "body: " + response.errorBody().toString());
                            mensajePerfilM.setValue("Error: " + errorReal);
                        }catch(Exception e){
                            e.printStackTrace();
                            getMensajeClaveM().setValue("Error al leer la respuesta del servidor");
                        }
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Log.d("error", "mennsaje: " + t.getMessage());
                    mensajePerfilM.setValue("Error: " + t.getMessage());

                }
            });


        }

    }


    public void cambiarClave(String actual, String nueva) {
        if (actual.isEmpty() || nueva.isEmpty()) {
            getMensajeClaveM().setValue("Por favor, complete todos los campos");
        } else {

            String token = ApiClient.leerToken(getApplication());
            ApiClient.MiServicioInmobiliaria sv = ApiClient.getServicio();
            Call<Void> call = sv.cambiarContrasenia(token, actual, nueva);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        mensajeClaveM.setValue("Clave Modificada");
                    } else {
                        try {
                            String errorReal = response.errorBody().string();
                            Log.d("error", "codigo: " + response.code());
                            Log.d("error", "mennsaje: " + response.message());
                            Log.d("error", "body: " + errorReal);
                            mensajeClaveM.setValue("Error: " + errorReal);
                        }catch (Exception e){
                            e.printStackTrace();
                            getMensajeClaveM().setValue("Error al leer la respuesta del servidor");

                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("error", "mennsaje: " + t.getMessage());
                    mensajeClaveM.setValue("Error: " + t.getMessage());
                }
            });

        }
    }



    }





