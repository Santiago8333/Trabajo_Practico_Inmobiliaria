package com.in.trabajo_practico_inmobiliaria.ui.inmuebles;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.FragmentInmueblesBinding;

public class InmueblesFragment extends Fragment {

    private InmueblesViewModel mViewModel;
    private FragmentInmueblesBinding b;

    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentInmueblesBinding.inflate(getLayoutInflater());





        return b.getRoot();
    }



}