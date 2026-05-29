package com.in.trabajo_practico_inmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentContratoDetalleBinding;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentContratosBinding;
import com.in.trabajo_practico_inmobiliaria.modelo.Contrato;

public class ContratoDetalleFragment extends Fragment {
    private FragmentContratoDetalleBinding b;

    private ContratoDetalleViewModel mViewModel;

    public static ContratoDetalleFragment newInstance() {
        return new ContratoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);
        b = FragmentContratoDetalleBinding.inflate(inflater, container, false);

        mViewModel.getContratoM().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                b.tvContratoFechaInicio.setText(contrato.getFechaInicio());
                b.tvContratoFechaFinalizacion.setText(contrato.getFechaFinalizacion());
                b.cbDisponibleContrato.setChecked(contrato.isEstado());
                b.tvContratoMontoAlquiler.setText(""+contrato.getMontoAlquiler());
                b.btnVerPagos.setEnabled(true);
            }
        });

        mViewModel.cargarDetalleContrato(getArguments());

        b.btnVerPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer idContratoActual = mViewModel.getContratoidM().getValue();


                    Bundle bundle = new Bundle();
                    bundle.putInt("idContrato", idContratoActual);

                   // Navigation.findNavController(v).navigate(R.id.nav_pagos, bundle);
            }
        });


        return b.getRoot();
    }



}