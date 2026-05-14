package com.in.trabajo_practico_inmobiliaria.ui.inicio;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentInicioBinding;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentInmueblesBinding;
import com.in.trabajo_practico_inmobiliaria.ui.perfil.PerfilViewModel;

public class InicioFragment extends Fragment {

    private InicioViewModel mViewModel;

    private FragmentInicioBinding b;

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentInicioBinding.inflate(getLayoutInflater());
        mViewModel = new ViewModelProvider(this).get(InicioViewModel.class);


        mViewModel.getMMapaActual().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.MapaActual>() {
            @Override
            public void onChanged(InicioViewModel.MapaActual mapaActual) {
                ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapaActual);
            }
        });

        mViewModel.obtenerMapa();


        return b.getRoot();
    }



}