package com.in.trabajo_practico_inmobiliaria.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.bumptech.glide.Glide;
import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentInmueblesBinding;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

public class detalle_inmuebleFragment extends Fragment {
    private FragmentDetalleInmuebleBinding b;

    private DetalleInmuebleViewModel mViewModel;

    public static detalle_inmuebleFragment newInstance() {
        return new detalle_inmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentDetalleInmuebleBinding.inflate(getLayoutInflater());
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        mViewModel.getInmuebleM().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                Glide.with(getContext())
                        .load(ApiClient.BASE_URL + inmueble.getImagen())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.house)
                        .into(b.tvImagen);


                b.tvCodigo.setText(String.valueOf(inmueble.getIdInmueble()));
                b.tvDireccion.setText(inmueble.getDireccion());
                b.tvPrecio.setText("$ " + inmueble.getValor());
                b.tvAmbiente.setText(String.valueOf(inmueble.getAmbientes()));
                b.tvTipo.setText(inmueble.getTipo());
                b.tvUso.setText(inmueble.getUso());
                b.cbDisponible.setChecked(inmueble.isDisponible());



                if (inmueble.isDisponible()) {
                    b.cbDisponible.setText("Inmueble Disponible");
                } else {
                    b.cbDisponible.setText("Inmueble No Disponible");
                }



            }
        });

        b.cbDisponible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean estaMarcado = ((CheckBox) view).isChecked();
                mViewModel.cambiarDispponibilidad(estaMarcado);
            }
        });


        mViewModel.getMensajeDisponibleM().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                b.cbDisponible.setText(s);
            }
        });


        mViewModel.cargarDetalleinmueble(getArguments());

        return b.getRoot();
    }



}