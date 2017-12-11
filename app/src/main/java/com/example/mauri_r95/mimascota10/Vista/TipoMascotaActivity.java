package com.example.mauri_r95.mimascota10.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mauri_r95.mimascota10.AdapterSimple;
import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Mascota;
import com.example.mauri_r95.mimascota10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase que carga los tipos de mascotas
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 **/
public class TipoMascotaActivity extends AppCompatActivity {

    private AdapterSimple adapter;
    private RecyclerView recyclerView;
    private List<String> tiposMascotas;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_mascota);

        //SET UP RECYCLERVIEW
        recyclerView = (RecyclerView)findViewById(R.id.recycler_tip_mas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        tiposMascotas = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        //ADAPTER
        adapter = new AdapterSimple(this,tiposMascotas);
        recyclerView.setAdapter(adapter);

        reference.child(FirebaseReference.REF_TIPO_MASCOTA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tiposMascotas.removeAll(tiposMascotas);
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String tipoMascota = ds.getValue(String.class);
                    tiposMascotas.add(tipoMascota);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                Boolean statesex = extras.getBoolean("sexo"); //
                String activity = extras.getString("activity"); //




                if(activity.equals("buscar")){
                    //se mandan 8 parametros
                    String categoria_b = extras.getString("categoria");
                    Boolean sexo_s = extras.getBoolean("sexo_s");
                    String tipo = tiposMascotas.get(recyclerView.getChildAdapterPosition(v)).toString();
                    //String edad = extras.getString("edad");
                    String comuna = extras.getString("comuna");

                    Intent intent1 = new Intent(TipoMascotaActivity.this, BuscarActivity.class);
                    //intent1.putExtra("edad", edad);
                    intent1.putExtra("categoria", categoria_b);
                    intent1.putExtra("sexo_s", sexo_s);
                    intent1.putExtra("sexo", statesex);
                    intent1.putExtra("tipo", tipo);
                    if (tipo.equals("Perro")){
                        intent1.putExtra("raza", "Raza");
                    }else if (tipo.equals("Ave")){
                        intent1.putExtra("raza", "Tipo de Ave");
                    }else{
                        intent1.putExtra("raza", "");
                    }

                    if (!tipo.equals("Perro")){
                        intent1.putExtra("tamano", "");
                    }else{
                        intent1.putExtra("tamano", "Tamaño");
                    }
                    intent1.putExtra("comuna", comuna);
                    intent1.putExtra("activity", activity);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();
                }else{
                    //se mandan 7 parametros
                    String imagen_nom = extras.getString("imagen_nom"); //
                    String cat_get = extras.getString("cat_get");
                    String segundo = extras.getString("1"); //
                    Mascota mascota = extras.getParcelable("mascota");
                    String mas_key = extras.getString("key"); //
                    mascota.setTipo(tiposMascotas.get(recyclerView.getChildAdapterPosition(v)).toString());

                    Intent intent = new Intent(TipoMascotaActivity.this, PubAnuncioActivity.class);
                    intent.putExtra("key", mas_key);
                    intent.putExtra("activity", activity);
                    intent.putExtra("imagen_nom", imagen_nom);
                    intent.putExtra("cat_get", cat_get);
                    if (mascota.getTipo().equals("Perro")){
                        mascota.setRaza("Raza");
                    }else if (mascota.getTipo().equals("Ave")){
                        mascota.setRaza("Tipo de Ave");
                    }else{
                        mascota.setRaza("");
                    }
                    if (!mascota.getTipo().equals("Perro")){
                        mascota.setTamano("");
                    }else{
                        mascota.setTamano("Tamaño");
                    }
                    intent.putExtra("mascota", mascota);
                    intent.putExtra("1", segundo);
                    //intent.putExtra("foto", bitmap);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }

                
                //Toast.makeText(getApplicationContext(), tiposMascotas.get(recyclerView.getChildAdapterPosition(v)), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
