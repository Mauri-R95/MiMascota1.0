package com.example.mauri_r95.mimascota10;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mauri_r95.mimascota10.Modelo.Mascota;

import java.util.List;

/** Adaptador del recycler view para cargar la lista de mascotas segun una estructura determinada
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MascotaViewHolder> implements View.OnClickListener {

    List<Mascota> mascotas;
    private View.OnClickListener listener;
    private int selectPosicion;

    public Adapter(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    @Override
    //erá quien devuelva el ViewHolder con el layout seteado que previamente definimos \res\layout\item_listview_main.xml
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_main, parent, false);
        MascotaViewHolder holder = new MascotaViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    //será quien se encargue de establecer los objetos en el ViewHolder
    public void onBindViewHolder(MascotaViewHolder holder, int position) {
        Mascota mascota = mascotas.get(position);
        holder.nombreT.setText(mascota.getNombre());
        holder.fechaComT.setText(mascota.getFecha() + " - " + mascota.getComuna());
        holder.categoriaT.setText(mascota.getTipo());
        holder.imagenURL(mascota.getImagen());
    }

    @Override
    //será quien devuelva la cantidad de items que se encuentra en la lista
    public int getItemCount() {
        return mascotas.size();
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

    public int getSelectionPosicion() {
        return selectPosicion;
    }

    public void setSelectPosicion(int selectPosicion) {
        this.selectPosicion = selectPosicion;
    }

    /** mantenedor del adaptar
     *
     * @author Mauri_R95
     * @since 1.0
     * @version 1.1 Cambios hechos
     */


    protected class  MascotaViewHolder extends RecyclerView.ViewHolder{

        TextView nombreT, fechaComT, categoriaT;
        ImageView foto, img_fav;

         public MascotaViewHolder(View itemView) {
            super(itemView);
            nombreT = (TextView)itemView.findViewById(R.id.nombre_item);
            fechaComT = (TextView)itemView.findViewById(R.id.fecha_item);
            categoriaT = (TextView)itemView.findViewById(R.id.categoria_item);
            foto = (ImageView)itemView.findViewById(R.id.imagVet);
            img_fav = (ImageView)itemView.findViewById(R.id.img_fav);

        }

        /**
         * Metodo para cargar imagen al ImageView
         * @param title direccion de la imagen de firebase
         */
        private void imagenURL(String title){
            Glide.with(itemView.getContext())
                    .load(title)
                    .crossFade()
                    .fitCenter()
                    .centerCrop()
                    .into(foto);
        }
    }


}
