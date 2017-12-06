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
import com.example.mauri_r95.mimascota10.Modelo.Usuario;
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
 * clase para cargar las diferentes comunas segun provicias
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class ComunaActivity extends AppCompatActivity {

    private AdapterSimple adapter;
    private RecyclerView recyclerView;
    private List<String> comunas;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comuna);


        //SET UP RECYCLERVIEW
        recyclerView = (RecyclerView)findViewById(R.id.recycler_com);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        comunas = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        //ADAPTER
        adapter = new AdapterSimple(this,comunas);
        recyclerView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        String provincia = extras.getString("provincia");
        reference.child(FirebaseReference.ref_comuna).child(provincia).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comunas.removeAll(comunas);
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String comuna = ds.getValue(String.class);
                    comunas.add(comuna);
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
                String activity = extras.getString("activity");
                Boolean statesex = extras.getBoolean("sexo");
                Intent intent = new Intent(ComunaActivity.this, PubAnuncioActivity.class);
                Intent intent1 = new Intent(ComunaActivity.this, EditarCuentaActivity.class);
                Intent intent2 = new Intent(ComunaActivity.this, BuscarActivity.class);
                if(extras.getParcelable("usuario") != null){
                    String user = extras.getString("user");
                    Usuario usuario = extras.getParcelable("usuario");
                    String imagen_nom = extras.getString("imagen_nom");
                    String segundo = extras.getString("1");

                    usuario.setComuna(comunas.get(recyclerView.getChildAdapterPosition(v)).toString());
                    intent1.putExtra("usuario",usuario);
                    intent1.putExtra("user",user);
                    intent1.putExtra("1", "segundo");
                    intent1.putExtra("imagen_nom", imagen_nom);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }else if(activity.equals("buscar")){
                    //Se mandan 8 parametros
                    String categoria_b = extras.getString("categoria");
                    Boolean sexo_s = extras.getBoolean("sexo_s");
                    String tipo = extras.getString("tipo");
                    String raza = extras.getString("raza");
                    //String edad = extras.getString("edad");
                    String tamano = extras.getString("tamano");
                    String comuna = comunas.get(recyclerView.getChildAdapterPosition(v)).toString();

                    //intent1.putExtra("edad", edad);
                    intent2.putExtra("raza", raza);
                    intent2.putExtra("categoria", categoria_b);
                    intent2.putExtra("sexo_s", sexo_s);
                    intent2.putExtra("sexo", statesex);
                    intent2.putExtra("tipo", tipo);
                    intent2.putExtra("tamano", tamano);
                    intent2.putExtra("comuna", comuna);
                    intent2.putExtra("activity",activity);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    finish();

                }else{
                    String segundo = extras.getString("1");
                    String cat_get = extras.getString("cat_get");
                    Mascota mascota = extras.getParcelable("mascota");
                    String mas_key = extras.getString("key");
                    String imagen_nom = extras.getString("imagen_nom");
                    mascota.setComuna(comunas.get(recyclerView.getChildAdapterPosition(v)).toString());

                    intent.putExtra("1", "segundo");
                    intent.putExtra("cat_get", cat_get);
                    intent.putExtra("key", mas_key);
                    intent.putExtra("activity", activity);
                    intent.putExtra("mascota", mascota);
                    intent.putExtra("imagen_nom", imagen_nom);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                finish();

                //Toast.makeText(getApplicationContext(), comunas.get(recyclerView.getChildAdapterPosition(v)), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
