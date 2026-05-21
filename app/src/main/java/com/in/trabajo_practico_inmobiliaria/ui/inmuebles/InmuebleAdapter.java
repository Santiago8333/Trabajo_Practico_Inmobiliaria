package com.in.trabajo_practico_inmobiliaria.ui.inmuebles;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {


    private List<Inmueble> listaInmuebles;
    private LayoutInflater inflater;
    //private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Inmueble inmueble);
    }


    public InmuebleAdapter(List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.listaInmuebles = inmuebles;
        this.inflater = inflater;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_inmueble, parent, false);
        return new ViewHolderInmueble(itemView);
    }

    //inlfar card
    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
        Inmueble inmuebleActual = listaInmuebles.get(position);
        holder.direccion.setText(inmuebleActual.getDireccion());

        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "AR"));
        String valorFormateado = nf.format(inmuebleActual.getValor());
        holder.precio.setText("$ " + valorFormateado);
        String rutaImagen = inmuebleActual.getImagen();
        String urlFinal = "";

        if (rutaImagen != null && !rutaImagen.isEmpty()) {

            rutaImagen = rutaImagen.replace("\\", "/");

            // Asegurar que haya una sola barra "/" entre la BASE_URL y la ruta
            if (rutaImagen.startsWith("/")) {
                urlFinal = ApiClient.BASE_URL + rutaImagen;
            } else {
                urlFinal = ApiClient.BASE_URL + "/" + rutaImagen;
            }
        }

        // Imprimir en consola para que veas qué URL se armó finalmente
        Log.d("GLIDE_URL", "Cargando imagen: " + urlFinal);


        Glide.with(holder.itemView.getContext())
                .load(urlFinal)
                .placeholder(R.drawable.loading)
                .error(R.drawable.house)
                .into(holder.foto);

        holder.itemView.setOnClickListener(v -> {
            //if (listener != null) listener.onItemClick(inmuebleActual);
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble",inmuebleActual);
            Navigation.findNavController(v)
                    .navigate(R.id.nav_detalle_inmueble,bundle);

        });


    }

    @Override
    public int getItemCount() {
        return listaInmuebles != null ? listaInmuebles.size() : 0;
    }

    public static class ViewHolderInmueble extends RecyclerView.ViewHolder {
        TextView direccion, superficie, precio;
        ImageView foto;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            //superficie = itemView.findViewById(R.id.tvAmbientes);
            precio = itemView.findViewById(R.id.tvPrecio);
            foto = itemView.findViewById(R.id.tvImagen);
        }
    }
}
