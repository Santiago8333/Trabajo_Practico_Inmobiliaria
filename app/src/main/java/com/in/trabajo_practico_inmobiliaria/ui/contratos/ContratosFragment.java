package com.in.trabajo_practico_inmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentContratosBinding;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.ui.inquilinos.InquilinosAdapter;

import java.util.List;

public class ContratosFragment extends Fragment {
    private FragmentContratosBinding b;

    private ContratosViewModel mViewModel;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        b = FragmentContratosBinding.inflate(inflater, container, false);

        mViewModel.getListaInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                ContratosAdapter adapter = new ContratosAdapter(inmuebles,getLayoutInflater());

                b.rccontratos.setAdapter(adapter);

                GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                b.rccontratos.setLayoutManager(glm);



            }
        });



        mViewModel.obtenerListaInmueblesContratoVigente();

        return b.getRoot();
    }



}