package com.example.mauri_r95.mimascota10.Vista;

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

import com.bumptech.glide.Glide;
import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Mascota;
import com.example.mauri_r95.mimascota10.Modelo.Usuario;
import com.example.mauri_r95.mimascota10.R;
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
    private TextView nombre, ubicacion, n_mas, n_publ;
    private LinearLayout mas_l, publi_l;
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
        n_publ = (TextView)findViewById(R.id.num_publicaciones_cuenta);
        mas_l = (LinearLayout)findViewById(R.id.la_mis_mascotas_cuenta);
        publi_l = (LinearLayout)findViewById(R.id.la_publicaciones_cuenta);

        //INICIALIZAR USUARIO
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child(FirebaseReference.REF_USUARIOS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Usuario user = ds.getValue(Usuario.class);
                    if(user.getEmail().equals(email)){
                        k_user = ds.getKey();
                        usuario = user;
                        Glide.with(getApplicationContext())
                                .load(usuario.getImagen())
                                .crossFade()
                                .fitCenter()
                                .centerCrop()
                                .into(imagen);
                        nombre.setText(usuario.getNombre().toString());
                        if(usuario.getComuna().length() != 0){
                            ubicacion.setText(usuario.getRegion()+"\n"+usuario.getComuna().toString());
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
        //extrae la cantidad de mascotas que tiene en mis mascotas el usuario
        reference.child(FirebaseReference.REF_MIS_MASCOTAS).addValueEventListener(new ValueEventListener() {
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

        //extrae la cantidad de mascotas que tiene en mis publicaciones el usuario
        reference.child(FirebaseReference.REF_MASCOTAS).addValueEventListener(new ValueEventListener() {
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
