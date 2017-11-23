package com.example.mauri_r95.mimascota10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mauri_r95.mimascota10.Modelos.Tamano;

import java.util.List;

/**
 * Created by Mauri_R95 on 04-10-2017.
 * Adaptador del recycler view para cargar la lista de los diferentes tamaños de perros
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class AdapterTamano extends RecyclerView.Adapter<AdapterTamano.TamanoViewHolder> implements View.OnClickListener{

    List<Tamano> tamanos;
    Context c;
    private View.OnClickListener listener;

    public AdapterTamano(Context c, List<Tamano> tamanos) {
        this.tamanos = tamanos;
        this.c = c;
    }

    @Override
    //erá quien devuelva el ViewHolder con el layout seteado que previamente definimos \res\layout\item_listview_main.xml
    public TamanoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_listview_main,parent,false);
        TamanoViewHolder holder = new TamanoViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    //será quien se encargue de establecer los objetos en el ViewHolder
    public void onBindViewHolder(TamanoViewHolder holder, int position) {
        Tamano tamano = tamanos.get(position);
        holder.nombreT.setText(tamano.getNombre());
        holder.abrevT.setText(tamano.getAbrev());
        holder.pesoT.setText("Peso: " + tamano.getPeso());
        holder.Imagen_URL(tamano.getImagen());
    }

    @Override
    //será quien devuelva la cantidad de items que se encuentra en la lista
    public int getItemCount() {
        return tamanos.size();
    }


    public void setOnClickListener(View.OnClickListener listener){this.listener = listener; }
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
    public class  TamanoViewHolder extends RecyclerView.ViewHolder{

        TextView nombreT, abrevT, pesoT;
        ImageView foto;

        public TamanoViewHolder(View itemView) {
            super(itemView);
            nombreT = (TextView)itemView.findViewById(R.id.nombre_item);
            abrevT = (TextView)itemView.findViewById(R.id.fecha_item);
            pesoT= (TextView)itemView.findViewById(R.id.categoria_item);
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