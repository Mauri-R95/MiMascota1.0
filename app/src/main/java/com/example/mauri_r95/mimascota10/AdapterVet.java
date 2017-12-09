package com.example.mauri_r95.mimascota10;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mauri_r95.mimascota10.Modelo.Veterinario;

import java.util.List;

/**
 * Created by Mauri_R95 on 08-12-2017.
 */

public class AdapterVet extends RecyclerView.Adapter<AdapterVet.VetViewHolder> implements View.OnClickListener{

    List<Veterinario> veterinarios;
    private View.OnClickListener listener;

    public AdapterVet(List<Veterinario> veterinarios) {
        this.veterinarios = veterinarios;
    }

    @Override
    public VetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_veterinario,parent,false);
        VetViewHolder vetViewHolder = new VetViewHolder(v);
        v.setOnClickListener(this);
        return vetViewHolder;
    }

    @Override
    public void onBindViewHolder(VetViewHolder holder, int position) {
        Veterinario veterinario = veterinarios.get(position);
        String distanciaS = (veterinario.getDistancia() + " m");
        holder.nombreV.setText(veterinario.getNombre());
        holder.direccionV.setText(veterinario.getDireccion());
        holder.distancia.setText(distanciaS);

    }

    @Override
    public int getItemCount() {
        return veterinarios.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    protected class VetViewHolder extends RecyclerView.ViewHolder{

        TextView nombreV, direccionV, distancia;

        public VetViewHolder(View itemView){
            super(itemView);

            nombreV = (TextView)itemView.findViewById(R.id.nombre_vet);
            direccionV = (TextView)itemView.findViewById(R.id.direc_vet);
            distancia = (TextView)itemView.findViewById(R.id.distance_vet);



        }

    }
}
