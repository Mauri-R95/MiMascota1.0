package com.example.mauri_r95.mimascota10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/** Adaptador del recycler view para cargar la lista con textos simples
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */

public class AdapterSimple extends RecyclerView.Adapter<AdapterSimple.AdapterSimpleViewHolder> implements View.OnClickListener{

    List<String> list;
    private Context c;
    private View.OnClickListener listener;

    public AdapterSimple(Context c, List<String> list) {
        this.c = c;
        this.list = list;
    }

    @Override
    //erá quien devuelva el ViewHolder con el layout seteado que previamente definimos \res\layout\item_listview_main.xml
    public AdapterSimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_recycler_simple,parent,false);
        AdapterSimpleViewHolder holder = new AdapterSimpleViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    //será quien se encargue de establecer los objetos en el ViewHolder
    public void onBindViewHolder(AdapterSimpleViewHolder holder, int position) {
        final String cate = list.get(position);
        holder.textView.setText(cate);

    }

    @Override
    //será quien devuelva la cantidad de items que se encuentra en la lista
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
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
    protected class  AdapterSimpleViewHolder extends RecyclerView.ViewHolder{

        TextView textView;


        public AdapterSimpleViewHolder(View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.text_r_simple);

        }

    }


}
