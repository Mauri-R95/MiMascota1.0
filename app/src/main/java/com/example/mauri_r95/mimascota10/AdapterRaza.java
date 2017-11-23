package com.example.mauri_r95.mimascota10;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mauri_r95.mimascota10.Modelos.Raza;

import java.util.List;

/**
 * Created by Mauri_R95 on 04-10-2017.
 * Adaptador del recycler view para cargar la lista de las diferentes razas
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */

public class AdapterRaza extends RecyclerView.Adapter<AdapterRaza.RazaViewHolder> implements View.OnClickListener{

    List<Raza> razas;
    private View.OnClickListener listener;

    public AdapterRaza(List<Raza> razas) {  this.razas = razas; }

    @Override
    public RazaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_raza,parent,false);
        RazaViewHolder holder = new RazaViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RazaViewHolder holder, int position) {
        Raza raza = razas.get(position);
        holder.nombre.setText(raza.getNombre());
        holder.Imagen_URL(raza.getImagen());

    }

    @Override
    public int getItemCount() { return razas.size(); }

    public void setOnClickListener(View.OnClickListener listener){ this.listener = listener; }
    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    /**
     * Created by Mauri_R95 on 04-10-2017.
     * Mantenedor del adaptador
     *
     * @author Mauri_R95
     * @since 1.0
     * @version 1.1 Cambios hechos
     */
    public class RazaViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        ImageView foto;

        public RazaViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView)itemView.findViewById(R.id.raza_item);
            foto = (ImageView)itemView.findViewById(R.id.img_raza_item);
        }

        private void Imagen_URL(String title){
            Glide.with(itemView.getContext())
                    .load(title)
                    .crossFade()
                    .fitCenter()
                    .centerCrop()
                    .into(foto);
        }
    }
}
