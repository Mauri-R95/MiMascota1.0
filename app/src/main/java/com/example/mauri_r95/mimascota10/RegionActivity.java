package com.example.mauri_r95.mimascota10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.mauri_r95.mimascota10.Modelos.Mascota;
import com.example.mauri_r95.mimascota10.Modelos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase que carga las regiones
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class RegionActivity extends AppCompatActivity {

    private AdapterSimple adapter;
    private RecyclerView recyclerView;
    private List<String> regiones;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        //SET UP RECYCLERVIEW
        recyclerView = (RecyclerView)findViewById(R.id.recycler_reg);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        regiones = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        //ADAPTER
        adapter = new AdapterSimple(this,regiones);
        recyclerView.setAdapter(adapter);

        reference.child(FirebaseReference.ref_region).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                regiones.removeAll(regiones);
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String region = ds.getValue(String.class);
                    regiones.add(region);
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

                Intent intent = new Intent(RegionActivity.this, ProvinciaActivity.class);

                if(extras.getParcelable("usuario") != null){
                    //se mandan 4 parametros
                    Usuario usuario = extras.getParcelable("usuario");
                    String key_user = extras.getString("user");
                    String imagen_nom = extras.getString("imagen_nom");
                    String segundo = extras.getString("1");
                    usuario.setRegion(regiones.get(recyclerView.getChildAdapterPosition(v)).toString());
                    intent.putExtra("user", key_user);
                    intent.putExtra("usuario",usuario);
                    intent.putExtra("1", segundo);
                    intent.putExtra("imagen_nom", imagen_nom);
                }else if(activity.equals("buscar")){
                    //se mandan 8 parametros
                    String categoria_b = extras.getString("categoria");
                    Boolean sexo_s = extras.getBoolean("sexo_s");
                    String tipo = extras.getString("tipo");
                    String raza = extras.getString("raza");
                    //String edad = extras.getString("edad");
                    String tamano = extras.getString("tamano");
                    String comuna = extras.getString("comuna");

                    //intent1.putExtra("edad", edad);
                    intent.putExtra("raza", raza);
                    intent.putExtra("categoria", categoria_b);
                    intent.putExtra("sexo_s", sexo_s);
                    intent.putExtra("sexo", statesex);
                    intent.putExtra("tipo", tipo);
                    intent.putExtra("tamano", tamano);
                    intent.putExtra("comuna", comuna);
                    intent.putExtra("activity",activity);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    //Se mandan 7 parametros
                    String segundo = extras.getString("1");
                    String cat_get = extras.getString("cat_get");
                    Mascota mascota = extras.getParcelable("mascota");
                    String mas_key = extras.getString("key");
                    String imagen_nom = extras.getString("imagen_nom");

                    intent.putExtra("1", "segundo");
                    intent.putExtra("cat_get", cat_get);
                    intent.putExtra("mascota", mascota);
                    intent.putExtra("key", mas_key);
                    intent.putExtra("activity", activity);
                    intent.putExtra("imagen_nom", imagen_nom);
                }
                String region = regiones.get(recyclerView.getChildAdapterPosition(v)).toString();
                intent.putExtra("region", region);
                startActivity(intent);

            }
        });
    }
}
