package com.in.trabajo_practico_inmobiliaria.ui.contratos;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.modelo.Inmueble;
import com.in.trabajo_practico_inmobiliaria.modelo.Pago;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PagosAdaper extends RecyclerView.Adapter<PagosAdaper.ViewHolderPagos>{
    private List<Pago> listaPagos;
    private LayoutInflater inflater;

    public PagosAdaper(List<Pago> listaPagos, LayoutInflater inflater) {
        this.listaPagos = listaPagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderPagos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_pagos,parent,false);
        return new PagosAdaper.ViewHolderPagos(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPagos holder, int position) {
            Pago pagoActual = listaPagos.get(position);

            holder.codigo.setText("ID: #" + pagoActual.getIdPago());

            holder.fechaPago.setText(pagoActual.getFechaPago());

            holder.detalle.setText(pagoActual.getDetalle());

            //formatear numero monto
            NumberFormat nf = NumberFormat.getInstance(new Locale("es", "AR"));
            String montoFormateado = nf.format(pagoActual.getMonto());
            holder.monto.setText(montoFormateado);


        if (pagoActual.isEstado()) {
            holder.estado.setText("PAGADO");
            holder.estado.setTextColor(Color.parseColor("#4CAF50")); // Verde
        } else {
            holder.estado.setText("PENDIENTE");
            holder.estado.setTextColor(Color.parseColor("#F44336")); // Rojo
        }

    }

    @Override
    public int getItemCount() {
        return listaPagos != null ? listaPagos.size() : 0;
    }

    public static class ViewHolderPagos extends RecyclerView.ViewHolder{
        TextView fechaPago,monto,detalle,estado,codigo;

        public  ViewHolderPagos(@NonNull View itemView){
            super(itemView);
            codigo = itemView.findViewById(R.id.tvCodigo);
            detalle = itemView.findViewById(R.id.tvDetalle);
            fechaPago = itemView.findViewById(R.id.tvFechaPago);
            monto = itemView.findViewById(R.id.tvMonto);
            estado = itemView.findViewById(R.id.tvEstado);
        }

    }
}
