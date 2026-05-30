package com.in.trabajo_practico_inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.request.ApiClient;
import com.in.trabajo_practico_inmobiliaria.ui.inquilinos.InquilinosAdapter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ContratosAdapter extends RecyclerView.Adapter<ContratosAdapter.ViewHolderContratos>{
    private List<Inmueble> listaInmuebles;
    private LayoutInflater inflater;

    public ContratosAdapter(List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.listaInmuebles = inmuebles;
        this.inflater = inflater;

    }


    @NonNull
    @Override
    public ContratosAdapter.ViewHolderContratos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_contratos,parent,false);
        return new ContratosAdapter.ViewHolderContratos(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContratos holder, int position) {
        Inmueble inmuebleActual = listaInmuebles.get(position);
        holder.direccion.setText(inmuebleActual.getDireccion());

        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "AR"));
        String valorFormateado = nf.format(inmuebleActual.getValor());

        holder.valor.setText("$ " + valorFormateado);

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


        holder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmuebleAlquilado", inmuebleActual);

                Navigation.findNavController(v)
                        .navigate(R.id.nav_contratoDetalle,bundle);
               // Log.d("btnverContrato","Click");
            }
        });





    }

    @Override
    public int getItemCount() {
        return listaInmuebles != null ? listaInmuebles.size() : 0;
    }


    public static class ViewHolderContratos extends RecyclerView.ViewHolder {
        TextView direccion,valor;
        ImageView foto;
        Button btnVer;

        public ViewHolderContratos(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            foto = itemView.findViewById(R.id.tvImagen);
            valor = itemView.findViewById(R.id.tvPrecio);
            btnVer = itemView.findViewById(R.id.btnVer);
        }
    }


}
