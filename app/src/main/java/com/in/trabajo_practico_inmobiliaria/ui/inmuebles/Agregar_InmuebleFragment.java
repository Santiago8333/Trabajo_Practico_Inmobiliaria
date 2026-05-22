package com.in.trabajo_practico_inmobiliaria.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentAgregarInmuebleBinding;

public class Agregar_InmuebleFragment extends Fragment {
    private FragmentAgregarInmuebleBinding b;

    private AgregarInmuebleViewModel mViewModel;
    private ActivityResultLauncher<Intent> selector;
    private Intent intent;

    public static Agregar_InmuebleFragment newInstance() {
        return new Agregar_InmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentAgregarInmuebleBinding.inflate(getLayoutInflater());
        mViewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);

        b.btnCargarImagen.setOnClickListener(v -> {

            selector.launch(intent);

        });

        abrirGaleria();


        mViewModel.getImagenSeleccionadaM().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                b.ivImagenInmueble.setImageURI(uri);
            }
        });

        //boton guardar inmueble
        b.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.cargarInmueble(
                        b.edDireccion.getText().toString(),
                        b.edUso.getText().toString(),
                        b.edTipo.getText().toString(),
                        b.edAmbientes.getText().toString(),
                        b.edSuperficie.getText().toString(),
                        b.edValor.getText().toString()
                );
            }
        });
        //observar mensaje
        mViewModel.getMensajeM().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                b.tvMensaje.setText(s);
            }
        });

        //limpiar si se guardo el inmueble
        mViewModel.getLimpiarFormularioM().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean limpiar) {
                    b.edDireccion.setText("");
                    b.edUso.setText("");
                    b.edTipo.setText("");
                    b.edAmbientes.setText("");
                    b.edSuperficie.setText("");
                    b.edValor.setText("");

                    b.ivImagenInmueble.setImageResource(0);
                    b.ivImagenInmueble.setBackgroundColor(android.graphics.Color.parseColor("#E0E0E0"));

                     //b.tvMensaje.setText("");
            }
        });
        return b.getRoot();
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selector = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult resultado) {
                mViewModel.recibirFoto(resultado);
                Log.d("galeria","onActivityResilt"+resultado.toString());
            }

        });

    }


}