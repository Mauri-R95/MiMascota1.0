package com.example.mauri_r95.mimascota10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mauri_r95.mimascota10.Modelos.Favoritos;
import com.example.mauri_r95.mimascota10.Modelos.Mascota;
import com.example.mauri_r95.mimascota10.Modelos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase que carga la informacion del usuario
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class MiCuentaActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private String email, k_user;
    private ImageView imagen;
    private TextView nombre, ubicacion, n_mas, n_fav, n_publ;
    private LinearLayout mas_l, fav_l, publi_l;
    private int num_mas = 0, num_fav = 0, num_publ = 0;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);

        //CARGAR VARIABLES
        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        //Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
        imagen = (ImageView)findViewById(R.id.img_user);
        nombre = (TextView)findViewById(R.id.nombre_cuenta);
        ubicacion = (TextView)findViewById(R.id.ubicacion_cuenta);
        n_mas = (TextView)findViewById(R.id.num_mascotas_cuenta);
        n_fav = (TextView)findViewById(R.id.num_favoritos_cuenta);
        n_publ = (TextView)findViewById(R.id.num_publicaciones_cuenta);
        mas_l = (LinearLayout)findViewById(R.id.la_mis_mascotas_cuenta);
        fav_l = (LinearLayout)findViewById(R.id.la_favoritos_cuenta);
        publi_l = (LinearLayout)findViewById(R.id.la_publicaciones_cuenta);

        //INICIALIZAR USUARIO
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child(FirebaseReference.ref_usuarios).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    usuario = ds.getValue(Usuario.class);
                    k_user = ds.getKey();
                    if(usuario.getEmail().equals(email)){
                        Glide.with(getApplicationContext())
                                .load(usuario.getImagen())
                                .crossFade()
                                .fitCenter()
                                .centerCrop()
                                .into(imagen);
                        nombre.setText(usuario.getNombre().toString());
                        if(usuario.getComuna().length() != 0){
                            ubicacion.setText(usuario.getComuna().toString());
                        }else{
                            ubicacion.setText("No Especificado");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference.child(FirebaseReference.ref_mis_mascotas).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                num_mas = 0;
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Mascota mascota = ds.getValue(Mascota.class);
                    if(mascota.getUsuario().equals(email)) {
                        num_mas++;
                    }
                }
                n_mas.setText(""+num_mas);
            }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });

        reference.child(FirebaseReference.ref_mis_favoritos).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                num_fav = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Favoritos favoritos = ds.getValue(Favoritos.class);
                        if(favoritos.getUsuario().equals(email)){
                            num_fav++;
                        }
                }
                n_fav.setText(""+num_fav);
            }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });

        reference.child(FirebaseReference.ref_mascotas).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                num_publ = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Mascota mascota = ds.getValue(Mascota.class);
                    if(mascota.getUsuario().equals(email)){
                        num_publ++;
                    }
                }
                n_publ.setText(""+num_publ);
            }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });

        fav_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                misActivity("Mis Favoritos");
            }
        });

        mas_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                misActivity("Mis Mascotas");

            }
        });

        publi_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                misActivity("Mis Publicaciones");
            }
        });





    }
    //AGREGAR BOTON AL ACTION BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cuenta, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //AGREGAR UNA ACCION AL BOTON DEL ACCION BAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.editar_cuenta){
            Intent intent = new Intent(MiCuentaActivity.this, EditarCuentaActivity.class);
            intent.putExtra("usuario", usuario);
            intent.putExtra("user", k_user);
            intent.putExtra("1", "primero");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo para pasar a MisActivity
     * @param activity Variable para colocar titulo en la siguiente actividad
     */
    private void misActivity(String activity){
        Intent intent = new Intent(MiCuentaActivity.this, MisActivity.class);
        intent.putExtra("activity", activity);
        intent.putExtra("email", email);
        startActivity(intent);
    }

}
