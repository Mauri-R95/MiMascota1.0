package com.example.mauri_r95.mimascota10.Vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Mascota;
import com.example.mauri_r95.mimascota10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CargatTagActivity extends AppCompatActivity {

        ProgressDialog progressDialog;
    private boolean findtagBool = false;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Mascota mascotaFound;
    private String keyF;
    private String tagid;
    private String scan_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargat_tag);

        Bundle extras = getIntent().getExtras();
        tagid = extras.getString("tag");
        scan_add = extras.getString("scan_add");
        //Toast.makeText(this, scan_add ,Toast.LENGTH_SHORT).show();
        //cargartag.setText("TagID: " + tagid);
        progressDialog = new ProgressDialog(CargatTagActivity.this);
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Cargando.. Por favor espere");
        progressDialog.show();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        mascotaFound = new Mascota();
        reference.child(FirebaseReference.ref_mascotas).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    Mascota mascota = ds.getValue(Mascota.class);
                    if (mascota.getTag().equals(tagid)) {
                        findtagBool = true;
                        mascotaFound = mascota;
                        keyF = key;


                    }

                }
                if(scan_add.equals("scan")) {
                    verificarMascotaEncontrada(findtagBool, mascotaFound, keyF);
                }else{
                    verificarTagAgregada(findtagBool,tagid);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });








    }

    public void verificarMascotaEncontrada(Boolean findtagBool, Mascota mascota, String keyF){
        if(findtagBool){
            progressDialog.dismiss();
            Toast.makeText(this, "Mascota Encontrada",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CargatTagActivity.this, MascotaActivity.class);
            intent.putExtra("activity", "tag");
            intent.putExtra("key", keyF);
            intent.putExtra("mascota", mascota);
            intent.putExtra("email", mascota.getUsuario());
            startActivity(intent);

        }else{
            progressDialog.dismiss();
            Toast.makeText(this, "Mascota No Registrada",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CargatTagActivity.this, EscanearTagActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void verificarTagAgregada(Boolean findtagBool,String tag){
        Bundle extras = getIntent().getExtras();
        String activity = extras.getString("activity");
        String segundo = extras.getString("1");
        String cat_get = extras.getString("cat_get");
        Mascota mascota = extras.getParcelable("mascota");
        String mas_key = extras.getString("key");
        String imagen_nom = extras.getString("imagen_nom");
        
        if(!findtagBool){
            progressDialog.dismiss();
            Toast.makeText(this, "Tag guardado correctamente",Toast.LENGTH_SHORT).show();
            mascota.setTag(tag);

            Intent intent1 = new Intent(CargatTagActivity.this, PubAnuncioActivity.class);
            intent1.putExtra("1", segundo);
            intent1.putExtra("cat_get", cat_get);
            intent1.putExtra("key", mas_key);
            intent1.putExtra("activity", activity);
            intent1.putExtra("mascota", mascota);
            intent1.putExtra("imagen_nom", imagen_nom);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            finish();

        }else{
            progressDialog.dismiss();
            Toast.makeText(this, "Tag se encuentra registrado",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CargatTagActivity.this, AgregarTagActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            intent.putExtra("1", segundo);
            intent.putExtra("cat_get", cat_get);
            intent.putExtra("key", mas_key);
            intent.putExtra("activity", activity);
            intent.putExtra("mascota", mascota);
            intent.putExtra("imagen_nom", imagen_nom);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
