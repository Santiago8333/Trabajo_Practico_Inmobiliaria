package com.in.trabajo_practico_inmobiliaria.ui.inquilinos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.ui.inmuebles.InmuebleAdapter;

import java.util.List;

public class InquilinosAdapter extends RecyclerView.Adapter<InquilinosAdapter.ViewHolderInquilinos>{
    private List<Inmueble> listaInmuebles;
    private LayoutInflater inflater;

    public InquilinosAdapter(List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.listaInmuebles = inmuebles;
        this.inflater = inflater;

    }

    @NonNull
    @Override
    public ViewHolderInquilinos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_inquilinos,parent,false);
        return new ViewHolderInquilinos(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInquilinos holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listaInmuebles != null ? listaInmuebles.size() : 0;
    }


    public static class ViewHolderInquilinos extends RecyclerView.ViewHolder {
        TextView direccion;
        ImageView foto;

        public ViewHolderInquilinos(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            foto = itemView.findViewById(R.id.tvImagen);
        }
    }









}
