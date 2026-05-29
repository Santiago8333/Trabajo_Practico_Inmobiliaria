package com.in.trabajo_practico_inmobiliaria.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentInquilinosBinding;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentInquilinosDetalleBinding;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.modelo.Inquilino;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

public class InquilinosDetalleFragment extends Fragment {
    private FragmentInquilinosDetalleBinding b;
    private InquilinosDetalleViewModel mViewModel;

    public static InquilinosDetalleFragment newInstance() {
        return new InquilinosDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InquilinosDetalleViewModel.class);
        b = FragmentInquilinosDetalleBinding.inflate(getLayoutInflater());


        mViewModel.getInquilinoM().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                b.tvNombreInquilino.setText("Nombre: "+inquilino.getNombre());
                b.tvApellidoInquilino.setText("Apellido: "+inquilino.getApellido());
                b.tvDniInquilino.setText("Dni: "+inquilino.getDni());
                b.tvEmailInquilino.setText("Email: "+inquilino.getEmail());
                b.tvTelefonoInquilino.setText("Telefono: "+inquilino.getTelefono());


            }
        });



        mViewModel.cargarDetalleinquilino(getArguments());

        return b.getRoot();
    }



}