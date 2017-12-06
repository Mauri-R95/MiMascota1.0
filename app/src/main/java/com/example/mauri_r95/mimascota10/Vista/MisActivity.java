package com.example.mauri_r95.mimascota10.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.mauri_r95.mimascota10.Adapter;
import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Favoritos;
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
 * clase que carga las diferentes lista de mascota segun propias, adopcion y busqueda
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class MisActivity extends AppCompatActivity {

    private String email, activity;
    FirebaseDatabase database;
    private Adapter adapter;
    private RecyclerView recyclerView;
    private List<Mascota> mascotaList;
    private List<String> mas_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis);

        database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        Bundle extras = getIntent().getExtras();
        activity = extras.getString("activity");
        email = extras.getString("email");
        setTitle(activity);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_mis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mascotaList = new ArrayList<>();
        mas_key = new ArrayList<>();
        adapter = new Adapter(mascotaList);
        recyclerView.setAdapter(adapter);


        if(activity.equals("Mis Mascotas")){
            ref.child(FirebaseReference.ref_mis_mascotas).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mascotaList.removeAll(mascotaList);
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Mascota mascota = ds.getValue(Mascota.class);
                        if(mascota.getUsuario().equals(email)){
                            mascotaList.add(mascota);
                            mas_key.add(ds.getKey());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override public void onCancelled(DatabaseError databaseError) {}
            });

        }else if(activity.equals("Mis Publicaciones")){
            ref.child(FirebaseReference.ref_mascotas).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mascotaList.removeAll(mascotaList);
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Mascota mascota = ds.getValue(Mascota.class);
                        if(mascota.getUsuario().equals(email)){
                            mascotaList.add(mascota);
                            mas_key.add(ds.getKey());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override public void onCancelled(DatabaseError databaseError) {}
            });
        }else if (activity.equals("Mis Favoritos")){
            ref.child(FirebaseReference.ref_mis_favoritos).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Favoritos favorito = ds.getValue(Favoritos.class);
                        if(favorito.getUsuario().equals(email)){
                            mas_key.add(favorito.getMascota());

                        }
                    }
                    mascotaList.removeAll(mascotaList);
                    for(String str : mas_key){
                        ref.child(FirebaseReference.ref_mascotas).child(str).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Mascota mascota = dataSnapshot.getValue(Mascota.class);
                                mascotaList.add(mascota);
                                adapter.notifyDataSetChanged();
                            }

                            @Override public void onCancelled(DatabaseError databaseError) {}
                        });
                    }
                }
                @Override public void onCancelled(DatabaseError databaseError) {}
            });

        }else if (activity.equals("buscar")){
            Bundle extras_b = getIntent().getExtras();
            final String categoria = extras_b.getString("categoria");
            final boolean sex = extras.getBoolean("sexo_s");
            final String sexo = extras.getString("sexo");
            final String tipo = extras.getString("tipo");
            final String raza = extras.getString("raza");
            final String tamano = extras.getString("tamano");
            final String comuna = extras.getString("comuna");
            Log.i("categoria", categoria);
            Log.i("sex", ""+sex);
            Log.i("sexo", sexo);
            Log.i("tipo", tipo);
            Log.i("raza", raza);
            Log.i("tamano", tamano);
            Log.i("comuna", comuna);


            ref.child(FirebaseReference.ref_mascotas).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    mascotaList.removeAll(mascotaList);
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Mascota mascota = ds.getValue(Mascota.class);
                        if(!categoria.equals("Categoria")){
                            if(mascota.getCategoria().equals(categoria)){
                                if(sex){
                                    if(mascota.getSexo().equals(sexo)) {
                                        if (!tipo.equals("Tipo de Mascota")) {
                                            if (mascota.getTipo().equals(tipo)) {
                                                if(tipo.equals("Perro") || tipo.equals("Ave")) {
                                                    if (!raza.equals("Raza") || !raza.equals("Tipo de Ave")) {
                                                        if (mascota.getRaza().equals(raza)) {
                                                            if (!tamano.equals("Tamaño")) {
                                                                if (mascota.getTamano().equals(tamano)) {
                                                                    if (!comuna.equals("Comuna")) {
                                                                        if (mascota.getComuna().equals(comuna)) {
                                                                            mascotaList.add(mascota);
                                                                            mas_key.add(ds.getKey());
                                                                        }
                                                                    } else {
                                                                        mascotaList.add(mascota);
                                                                        mas_key.add(ds.getKey());
                                                                    }
                                                                }
                                                            } else {
                                                                if (!comuna.equals("Comuna")) {
                                                                    if (mascota.getComuna().equals(comuna)) {
                                                                        mascotaList.add(mascota);
                                                                        mas_key.add(ds.getKey());
                                                                    }
                                                                } else {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        if (tipo.equals("Perro")) {
                                                            if (!tamano.equals("Tamaño")) {
                                                                if (mascota.getTamano().equals(tamano)) {
                                                                    if (!comuna.equals("Comuna")) {
                                                                        if (mascota.getComuna().equals(comuna)) {
                                                                            mascotaList.add(mascota);
                                                                            mas_key.add(ds.getKey());
                                                                        }
                                                                    } else {
                                                                        mascotaList.add(mascota);
                                                                        mas_key.add(ds.getKey());
                                                                    }
                                                                }
                                                            } else {
                                                                if (!comuna.equals("Comuna")) {
                                                                    if (mascota.getComuna().equals(comuna)) {
                                                                        mascotaList.add(mascota);
                                                                        mas_key.add(ds.getKey());
                                                                    }
                                                                } else {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            }
                                                        } else if (tipo.equals("Tipo de Ave")) {
                                                            if (!comuna.equals("Comuna")) {
                                                                if (mascota.getComuna().equals(comuna)) {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            } else {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }

                                                        }
                                                    }
                                                }else{
                                                    if (!comuna.equals("Comuna")) {
                                                        if (mascota.getComuna().equals(comuna)) {
                                                            mascotaList.add(mascota);
                                                            mas_key.add(ds.getKey());
                                                        }
                                                    } else {
                                                        mascotaList.add(mascota);
                                                        mas_key.add(ds.getKey());
                                                    }
                                                }
                                            }
                                        } else {
                                            if (!comuna.equals("Comuna")) {
                                                if (mascota.getComuna().equals(comuna)) {
                                                    mascotaList.add(mascota);
                                                    mas_key.add(ds.getKey());
                                                }
                                            } else {
                                                mascotaList.add(mascota);
                                                mas_key.add(ds.getKey());
                                            }
                                        }
                                    }
                                }else{
                                    if (!tipo.equals("Tipo de Mascota")) {
                                        if (mascota.getTipo().equals(tipo)) {
                                            if(tipo.equals("Perro") || tipo.equals("Ave")) {
                                                if (!raza.equals("Raza") || !raza.equals("Tipo de Ave")) {
                                                    if (mascota.getRaza().equals(raza)) {
                                                        if (!tamano.equals("Tamaño")) {
                                                            if (mascota.getTamano().equals(tamano)) {
                                                                if (!comuna.equals("Comuna")) {
                                                                    if (mascota.getComuna().equals(comuna)) {
                                                                        mascotaList.add(mascota);
                                                                        mas_key.add(ds.getKey());
                                                                    }
                                                                } else {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            }
                                                        } else {
                                                            if (!comuna.equals("Comuna")) {
                                                                if (mascota.getComuna().equals(comuna)) {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            } else {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    if (tipo.equals("Perro")) {
                                                        if (!tamano.equals("Tamaño")) {
                                                            if (mascota.getTamano().equals(tamano)) {
                                                                if (!comuna.equals("Comuna")) {
                                                                    if (mascota.getComuna().equals(comuna)) {
                                                                        mascotaList.add(mascota);
                                                                        mas_key.add(ds.getKey());
                                                                    }
                                                                } else {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            }
                                                        } else {
                                                            if (!comuna.equals("Comuna")) {
                                                                if (mascota.getComuna().equals(comuna)) {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            } else {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        }
                                                    } else if (tipo.equals("Tipo de Ave")) {
                                                        if (!comuna.equals("Comuna")) {
                                                            if (mascota.getComuna().equals(comuna)) {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        } else {
                                                            mascotaList.add(mascota);
                                                            mas_key.add(ds.getKey());
                                                        }

                                                    }
                                                }
                                            }else{
                                                if (!comuna.equals("Comuna")) {
                                                    if (mascota.getComuna().equals(comuna)) {
                                                        mascotaList.add(mascota);
                                                        mas_key.add(ds.getKey());
                                                    }
                                                } else {
                                                    mascotaList.add(mascota);
                                                    mas_key.add(ds.getKey());
                                                }
                                            }
                                        }
                                    } else {
                                        if (!comuna.equals("Comuna")) {
                                            if (mascota.getComuna().equals(comuna)) {
                                                mascotaList.add(mascota);
                                                mas_key.add(ds.getKey());
                                            }
                                        } else {
                                            mascotaList.add(mascota);
                                            mas_key.add(ds.getKey());
                                        }
                                    }
                                }
                            }
                        }else{
                            if(sex){
                                if(mascota.getSexo().equals(sexo)) {
                                    if (!tipo.equals("Tipo de Mascota")) {
                                        if (mascota.getTipo().equals(tipo)) {
                                            if(tipo.equals("Perro") || tipo.equals("Ave")) {
                                                if (!raza.equals("Raza") || !raza.equals("Tipo de Ave")) {
                                                    if (mascota.getRaza().equals(raza)) {
                                                        if (!tamano.equals("Tamaño")) {
                                                            if (mascota.getTamano().equals(tamano)) {
                                                                if (!comuna.equals("Comuna")) {
                                                                    if (mascota.getComuna().equals(comuna)) {
                                                                        mascotaList.add(mascota);
                                                                        mas_key.add(ds.getKey());
                                                                    }
                                                                } else {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            }
                                                        } else {
                                                            if (!comuna.equals("Comuna")) {
                                                                if (mascota.getComuna().equals(comuna)) {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            } else {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    if (tipo.equals("Perro")) {
                                                        if (!tamano.equals("Tamaño")) {
                                                            if (mascota.getTamano().equals(tamano)) {
                                                                if (!comuna.equals("Comuna")) {
                                                                    if (mascota.getComuna().equals(comuna)) {
                                                                        mascotaList.add(mascota);
                                                                        mas_key.add(ds.getKey());
                                                                    }
                                                                } else {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            }
                                                        } else {
                                                            if (!comuna.equals("Comuna")) {
                                                                if (mascota.getComuna().equals(comuna)) {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            } else {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        }
                                                    } else if (tipo.equals("Tipo de Ave")) {
                                                        if (!comuna.equals("Comuna")) {
                                                            if (mascota.getComuna().equals(comuna)) {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        } else {
                                                            mascotaList.add(mascota);
                                                            mas_key.add(ds.getKey());
                                                        }

                                                    }
                                                }
                                            }else{
                                                if (!comuna.equals("Comuna")) {
                                                    if (mascota.getComuna().equals(comuna)) {
                                                        mascotaList.add(mascota);
                                                        mas_key.add(ds.getKey());
                                                    }
                                                } else {
                                                    mascotaList.add(mascota);
                                                    mas_key.add(ds.getKey());
                                                }
                                            }
                                        }
                                    } else {
                                        if (!comuna.equals("Comuna")) {
                                            if (mascota.getComuna().equals(comuna)) {
                                                mascotaList.add(mascota);
                                                mas_key.add(ds.getKey());
                                            }
                                        } else {
                                            mascotaList.add(mascota);
                                            mas_key.add(ds.getKey());
                                        }
                                    }
                                }
                            }else{
                                if (!tipo.equals("Tipo de Mascota")) {
                                    if (mascota.getTipo().equals(tipo)) {
                                        if(tipo.equals("Perro") || tipo.equals("Ave")) {
                                            if (!raza.equals("Raza") || !raza.equals("Tipo de Ave")) {
                                                if (mascota.getRaza().equals(raza)) {
                                                    if (!tamano.equals("Tamaño")) {
                                                        if (mascota.getTamano().equals(tamano)) {
                                                            if (!comuna.equals("Comuna")) {
                                                                if (mascota.getComuna().equals(comuna)) {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            } else {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        }
                                                    } else {
                                                        if (!comuna.equals("Comuna")) {
                                                            if (mascota.getComuna().equals(comuna)) {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        } else {
                                                            mascotaList.add(mascota);
                                                            mas_key.add(ds.getKey());
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (tipo.equals("Perro")) {
                                                    if (!tamano.equals("Tamaño")) {
                                                        if (mascota.getTamano().equals(tamano)) {
                                                            if (!comuna.equals("Comuna")) {
                                                                if (mascota.getComuna().equals(comuna)) {
                                                                    mascotaList.add(mascota);
                                                                    mas_key.add(ds.getKey());
                                                                }
                                                            } else {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        }
                                                    } else {
                                                        if (!comuna.equals("Comuna")) {
                                                            if (mascota.getComuna().equals(comuna)) {
                                                                mascotaList.add(mascota);
                                                                mas_key.add(ds.getKey());
                                                            }
                                                        } else {
                                                            mascotaList.add(mascota);
                                                            mas_key.add(ds.getKey());
                                                        }
                                                    }
                                                } else if (tipo.equals("Tipo de Ave")) {
                                                    if (!comuna.equals("Comuna")) {
                                                        if (mascota.getComuna().equals(comuna)) {
                                                            mascotaList.add(mascota);
                                                            mas_key.add(ds.getKey());
                                                        }
                                                    } else {
                                                        mascotaList.add(mascota);
                                                        mas_key.add(ds.getKey());
                                                    }

                                                }
                                            }
                                        }else{
                                            if (!comuna.equals("Comuna")) {
                                                if (mascota.getComuna().equals(comuna)) {
                                                    mascotaList.add(mascota);
                                                    mas_key.add(ds.getKey());
                                                }
                                            } else {
                                                mascotaList.add(mascota);
                                                mas_key.add(ds.getKey());
                                            }
                                        }
                                    }
                                } else {
                                    if (!comuna.equals("Comuna")) {
                                        if (mascota.getComuna().equals(comuna)) {
                                            mascotaList.add(mascota);
                                            mas_key.add(ds.getKey());
                                        }
                                    } else {
                                        mascotaList.add(mascota);
                                        mas_key.add(ds.getKey());
                                    }
                                }
                            }
                        }


                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mascota mascota = mascotaList.get(recyclerView.getChildAdapterPosition(v));
                int pos = recyclerView.getChildAdapterPosition(v);
                String key = mas_key.get(pos);
                Intent intent = new Intent(MisActivity.this, MascotaActivity.class);
                intent.putExtra("activity", activity);
                intent.putExtra("key", key);
                intent.putExtra("mascota", mascota);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


    }
}
