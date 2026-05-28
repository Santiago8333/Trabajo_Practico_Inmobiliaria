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


        mViewModel.getInmuebleM().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                Glide.with(getContext())
                        .load(ApiClient.BASE_URL + "/" + inmueble.getImagen())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.house)
                        .into(b.ivImagen);


            }
        });

        mViewModel.cargarDetalleinmueble(getArguments());

        return b.getRoot();
    }



}