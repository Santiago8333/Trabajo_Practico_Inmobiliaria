package com.in.trabajo_practico_inmobiliaria.ui.inmuebles;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentAgregarInmuebleBinding;

public class Agregar_InmuebleFragment extends Fragment {
    private FragmentAgregarInmuebleBinding b;

    private AgregarInmuebleViewModel mViewModel;

    public static Agregar_InmuebleFragment newInstance() {
        return new Agregar_InmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentAgregarInmuebleBinding.inflate(getLayoutInflater());
        mViewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);

        b.btnCargarImagen.setOnClickListener(v -> {
            abrirGaleria.launch("image/*");
        });


        mViewModel.getImagenSeleccionadaM().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                if (uri != null) {

                    Glide.with(requireContext())
                            .load(uri)
                            .placeholder(R.drawable.loading)
                            .into(b.ivImagenInmueble);
                }
            }
        });



        return b.getRoot();
    }

    private final ActivityResultLauncher<String> abrirGaleria = registerForActivityResult(
        new ActivityResultContracts.GetContent(),
        new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                if (uri != null) {

                    mViewModel.setImagen(uri);
                }
            }
        });
}