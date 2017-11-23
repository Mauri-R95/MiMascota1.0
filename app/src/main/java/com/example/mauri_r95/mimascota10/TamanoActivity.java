package com.example.mauri_r95.mimascota10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.mauri_r95.mimascota10.Modelos.Mascota;
import com.example.mauri_r95.mimascota10.Modelos.Tamano;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TamanoActivity extends AppCompatActivity {

    private AdapterTamano adapter;
    private RecyclerView recyclerView;
    private List<Tamano> tamanos;
    private FirebaseDatabase database;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamano);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_tam);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        tamanos = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        adapter = new AdapterTamano(this, tamanos);
        recyclerView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        Mascota mascota = extras.getParcelable("mascota");
        if(mascota != null) {
            tipo = mascota.getTipo();
        }else{
            tipo = extras.getString("tipo");
        }
        reference.child(FirebaseReference.ref_tamano).child(tipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tamanos.removeAll(tamanos);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Tamano tamano = ds.getValue(Tamano.class);
                    tamanos.add(tamano);
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
                Boolean statesex = extras.getBoolean("sexo");
                String activity = extras.getString("activity");
                Tamano tamano = tamanos.get(recyclerView.getChildAdapterPosition(v));
                if(activity.equals("buscar")){
                    //se mandan 8 parametros
                    String categoria_b = extras.getString("categoria");
                    Boolean sexo_s = extras.getBoolean("sexo_s");
                    String raza = extras.getString("raza");
                    //String edad = extras.getString("edad");
                    String comuna = extras.getString("comuna");

                    Intent intent1 = new Intent(TamanoActivity.this, BuscarActivity.class);
                    //intent1.putExtra("edad", edad);
                    intent1.putExtra("raza", raza);
                    intent1.putExtra("categoria", categoria_b);
                    intent1.putExtra("sexo_s", sexo_s);
                    intent1.putExtra("sexo", statesex);
                    intent1.putExtra("tipo", tipo);
                    intent1.putExtra("tamano", tamano.getNombre());
                    intent1.putExtra("comuna", comuna);
                    intent1.putExtra("activity",activity);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();
                }else {
                    //se mandan 7 parametros
                    String segundo = extras.getString("1");
                    String cat_get = extras.getString("cat_get");
                    Mascota mascota = extras.getParcelable("mascota");
                    String mas_key = extras.getString("key");
                    String imagen_nom = extras.getString("imagen_nom");
                    mascota.setTamano(tamano.getNombre());

                    Intent intent = new Intent(TamanoActivity.this, PublicarAnuncioActivity.class);
                    intent.putExtra("1", "segundo");
                    intent.putExtra("cat_get", cat_get);
                    intent.putExtra("key", mas_key);
                    intent.putExtra("activity", activity);
                    intent.putExtra("mascota", mascota);
                    intent.putExtra("imagen_nom", imagen_nom);
                    intent.putExtra("sexo", statesex);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
