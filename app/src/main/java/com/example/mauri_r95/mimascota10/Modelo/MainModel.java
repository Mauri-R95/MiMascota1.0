package com.example.mauri_r95.mimascota10.Modelo;

import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mauri_R95 on 01-12-2017.
 */

public class MainModel{


    private List<Mascota> mMascotas;
    private List<String> sMas_key;
    FirebaseDatabase database;
    DatabaseReference reference;


    public MainModel(){
        mMascotas = new ArrayList<>();
        sMas_key = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference= database.getReference();

    }

    public List<Mascota> inicializarMascotas(){

        reference.child(FirebaseReference.REF_MASCOTAS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMascotas.removeAll(mMascotas);
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String key = ds.getKey();
                    Mascota mascota = ds.getValue(Mascota.class);
                    sMas_key.add(key);
                    mMascotas.add(mascota);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return mMascotas;
    }

    public List<Mascota> getMascotas() {
        return mMascotas;
    }

    public List<String> getMasKey() {
        return sMas_key;
    }


}
