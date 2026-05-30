package com.in.trabajo_practico_inmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentContratosBinding;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentPagosBinding;
import com.in.trabajo_practico_inmobiliaria.modelo.Pago;

import java.util.List;

public class PagosFragment extends Fragment {
    private FragmentPagosBinding b;

    private PagosViewModel mViewModel;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);

        b = FragmentPagosBinding.inflate(inflater, container, false);

        mViewModel.getListaPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagosAdaper adaper = new PagosAdaper(pagos,getLayoutInflater());

                b.rcPagos.setAdapter(adaper);

                GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);

                b.rcPagos.setLayoutManager(glm);

            }
        });

        mViewModel.cargarPagos(getArguments());


        return b.getRoot();
    }


}