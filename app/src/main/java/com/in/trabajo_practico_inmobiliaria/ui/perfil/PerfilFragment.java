package com.in.trabajo_practico_inmobiliaria.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentPerfilBinding;
import com.in.trabajo_practico_inmobiliaria.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    private FragmentPerfilBinding b;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentPerfilBinding.inflate(inflater,container,false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        mViewModel.cargarPerfil();
        mViewModel.getPropietarioM().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                b.edCodigo.setText(String.valueOf(propietario.getIdPropietario()));
                b.edDni.setText(propietario.getDni());
                b.edApellido.setText(propietario.getApellido());
                b.edNombre.setText(propietario.getNombre());
                b.edEmail.setText(propietario.getEmail());
                b.edTelefono.setText(propietario.getTelefono());
                b.edContrasena.setText(propietario.getClave());

            }
        });

        //encuchar evento de boton
        b.btBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.CambiarEstadoBoton();
            }
        });
        mViewModel.getBotonMensajeM().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                b.btBoton.setText(s);
            }
        });

    }

}