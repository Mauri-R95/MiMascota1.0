package com.example.mauri_r95.mimascota10.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mauri_r95.mimascota10.AdapterRaza;
import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Mascota;
import com.example.mauri_r95.mimascota10.Modelo.Raza;
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
 * clase que carga las razas
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class RazaActivity extends AppCompatActivity {

    private AdapterRaza adapter;
    private RecyclerView recyclerView;
    private List<Raza> razas;
    private FirebaseDatabase database;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raza);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_raza);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        razas = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        adapter = new AdapterRaza(razas);
        recyclerView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        Mascota mascota = extras.getParcelable("mascota");
        if(mascota != null){
             tipo = mascota.getTipo();
        }else{
             tipo = extras.getString("tipo");
        }

        reference.child(FirebaseReference.ref_raza).child(tipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                razas.removeAll(razas);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Raza raza = ds.getValue(Raza.class);
                    razas.add(raza);
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
                Raza raza = razas.get(recyclerView.getChildAdapterPosition(v));

                if(activity.equals("buscar")){
                    //se mandan 8 parametros
                    String categoria_b = extras.getString("categoria");
                    Boolean sexo_s = extras.getBoolean("sexo_s");
                    //String edad = extras.getString("edad");
                    String tamano = extras.getString("tamano");
                    String comuna = extras.getString("comuna");

                    Intent intent1 = new Intent(RazaActivity.this, BuscarActivity.class);
                    //intent1.putExtra("edad", edad);
                    intent1.putExtra("raza", raza.getNombre());
                    intent1.putExtra("categoria", categoria_b);
                    intent1.putExtra("sexo_s", sexo_s);
                    intent1.putExtra("sexo", statesex);
                    intent1.putExtra("tipo", tipo);
                    intent1.putExtra("tamano", tamano);
                    intent1.putExtra("comuna", comuna);
                    intent1.putExtra("activity",activity);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();

                }else {
                    //se mandan 7 parametros
                    String cat_get = extras.getString("cat_get");
                    Mascota mascota = extras.getParcelable("mascota"); //
                    String mas_key = extras.getString("key"); //
                    String imagen_nom = extras.getString("imagen_nom"); //
                    mascota.setRaza(raza.getNombre());
                    Intent intent = new Intent(RazaActivity.this, PubAnuncioActivity.class);
                    intent.putExtra("1", "segundo");
                    intent.putExtra("cat_get", cat_get);
                    intent.putExtra("key", mas_key);
                    intent.putExtra("activity", activity);
                    intent.putExtra("mascota", mascota);
                    intent.putExtra("imagen_nom", imagen_nom);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                //Toast.makeText(getApplicationContext(), raza.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
