package com.example.mauri_r95.mimascota10.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * clase para cargar las diferentes categorias (adopcion, perdida)
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class CategoriaActivity extends AppCompatActivity {

    private AdapterSimple adapter;
    private RecyclerView recyclerView;
    private List<String> categorias;
    private FirebaseDatabase database;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        //SET UP RECYCLERVIEW
        recyclerView = (RecyclerView)findViewById(R.id.recycler_cat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        categorias = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        //ADAPTER
        adapter = new AdapterSimple(this,categorias);
        recyclerView.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        activity = extras.getString("activity");

        reference.child(FirebaseReference.ref_categoria).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categorias.removeAll(categorias);
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String categoria = ds.getValue(String.class);
                    if(activity.equals("buscar")){
                        if(!categoria.equals("Mis Mascotas")) {
                            categorias.add(categoria);
                        }
                    }else{
                        categorias.add(categoria);
                    }

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


                if(activity.equals("buscar")){
                    //se mandan 8 parametros
                    String categoria_b = categorias.get(recyclerView.getChildAdapterPosition(v)).toString();
                    Boolean sexo_s = extras.getBoolean("sexo_s");
                    String tipo = extras.getString("tipo");
                    String raza = extras.getString("raza");
                    String tamano = extras.getString("tamano");
                    //String edad = extras.getString("edad");
                    String comuna = extras.getString("comuna");

                    Intent intent1 = new Intent(CategoriaActivity.this, BuscarActivity.class);
                    intent1.putExtra("categoria", categoria_b);
                    intent1.putExtra("sexo_s", sexo_s);
                    intent1.putExtra("sexo", statesex);
                    intent1.putExtra("tipo", tipo);
                    intent1.putExtra("raza", raza);
                    intent1.putExtra("tamano", tamano);
                    //intent1.putExtra("edad", edad);
                    intent1.putExtra("comuna", comuna);
                    intent1.putExtra("activity", activity);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();
                }else{
                    //se mandan 7 parametros
                    String cat_get = extras.getString("cat_get"); //cambio de categoria
                    String segundo = extras.getString("1"); //
                    Mascota mascota = extras.getParcelable("mascota"); //
                    String mas_key = extras.getString("key"); //
                    String imagen_nom = extras.getString("imagen_nom");
                    mascota.setCategoria(categorias.get(recyclerView.getChildAdapterPosition(v)).toString());
                    Intent intent = new Intent(CategoriaActivity.this, PubAnuncioActivity.class);
                    intent.putExtra("1", segundo);
                    intent.putExtra("key", mas_key);
                    intent.putExtra("activity", activity);
                    intent.putExtra("mascota", mascota);
                    intent.putExtra("imagen_nom", imagen_nom);
                    intent.putExtra("cat_get", cat_get);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                //Toast.makeText(getApplicationContext(), categorias.get(recyclerView.getChildAdapterPosition(v)), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
