package com.example.mauri_r95.mimascota10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mauri_r95.mimascota10.Modelos.Favoritos;
import com.example.mauri_r95.mimascota10.Modelos.Mascota;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase que carga la informacion de la mascota
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class MascotaActivity extends AppCompatActivity  {

    Mascota mascota;
    ImageView foto, img_fav;
    TextView nombre, fecha, fecha_nac, comuna, tip_raz, tip_raz_T, categoria, sexo, tamano, desc;
    LinearLayout li_tam;
    private FirebaseDatabase database;
    private Favoritos favoritos = new Favoritos();
    private Button btn_fav;
    private Boolean fav = false;
    private String activity, mas_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota);

        //CARGAR OBJETO
        final Bundle extras = getIntent().getExtras();
        activity = extras.getString("activity");
        mascota =  extras.getParcelable("mascota");
        favoritos.setMascota(extras.getString("key"));
        favoritos.setUsuario(extras.getString("email"));
        mas_key = extras.getString("key");
        //Toast.makeText(getApplicationContext(), mas_key, Toast.LENGTH_SHORT).show();

        //BOTON FAVORITO
        img_fav = (ImageView)findViewById(R.id.img_fav_mas);
        btn_fav = (Button)findViewById(R.id.btn_fav_mas);


        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        //VERIFICA SI LA MASCOTA ESTA EN LA SECCION DE FAVORITOS DEL USUARIO
        reference.child(FirebaseReference.ref_mis_favoritos).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Favoritos favorito = ds.getValue(Favoritos.class);
                    if(favorito.getMascota().equals(favoritos.getMascota()) && favorito.getUsuario().equals(favoritos.getUsuario())){
                        btn_fav.setText("Eliminar de Favoritos");
                        fav = true;
                        img_fav.setImageDrawable(getResources().getDrawable(R.drawable.favorito_act));
                    }else{
                        btn_fav.setText("Agregar a Favoritos");
                        img_fav.setImageDrawable(getResources().getDrawable(R.drawable.favorito_des));
                        fav = false;
                    }
                }
            }

            @Override public void onCancelled(DatabaseError databaseError) {}
        });
        //FOTO
        foto = (ImageView)findViewById(R.id.imageView_pet);
        Glide.with(MascotaActivity.this)
                .load(mascota.getImagen()) //cargar el link
                //.crossFade()
                //.fitCenter() //acomodar la foto
                //.centerCrop() //ajustar foto
                .into(foto);

        //NOMBRE
        nombre = (TextView)findViewById(R.id.nombre_pet);
        nombre.setText(mascota.getNombre());
        //FECHA
        fecha = (TextView)findViewById(R.id.fecha_pet);
        fecha.setText(mascota.getFecha());
        //FECHA DE NACIMIENTO
        fecha_nac = (TextView)findViewById(R.id.fecha_nac);
        fecha_nac.setText("fecha de nacimiento: " + mascota.getFechaNac());
        // COMUNA
        comuna = (TextView)findViewById(R.id.text_com_pet);
        comuna.setText(mascota.getComuna());
        // TIPO Y RAZA
        tip_raz = (TextView)findViewById(R.id.text_tip_pet);
        tip_raz_T = (TextView)findViewById(R.id.tipo_raza);
        if(mascota.getRaza().isEmpty()){
            tip_raz_T.setText("Tipo");
            tip_raz.setText(mascota.getTipo());
        }else{
            tip_raz_T.setText("Tipo - Raza");
            tip_raz.setText(mascota.getTipo() + " - " + mascota.getRaza());
        }
        //CATEGORIA
        categoria = (TextView)findViewById(R.id.text_cat_pet);
        categoria.setText(mascota.getCategoria());
        //SEXO
        sexo = (TextView)findViewById(R.id.text_sex_pet);
        sexo.setText(mascota.getSexo());

        //TAMAÑO
        tamano = (TextView)findViewById(R.id.text_tam_pet);
        li_tam = (LinearLayout)findViewById(R.id.li_tam_pet);
        if(mascota.getTamano().isEmpty()){
            li_tam.setVisibility(View.GONE);
        }else{
            tamano.setText(mascota.getTamano());
        }
        //DESCRIPCION
        desc = (TextView)findViewById(R.id.text_des_pet);
        if(mascota.getDescripcion().isEmpty()){
            desc.setText("Sin Descripción");
        }else{
            desc.setText(mascota.getDescripcion());
        }
    }

    //AGREGAR BOTON AL ACTION BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mascota, menu);
        //Toast.makeText(getApplicationContext(),""+menu.getItem(0).isChecked(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), user_key, Toast.LENGTH_SHORT).show();
        if(extras.getString("activity").equals("main")
                || extras.getString("activity").equals("Mis Favoritos")
                || extras.getString("activity").equals("buscar")) {
            menu.getItem(0).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    //AGREGAR UNA ACCION AL BOTON DEL ACCION BAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.editar_mascota){
            Intent intent = new Intent(MascotaActivity.this, PubAnuncioActivity.class);
            intent.putExtra("activity", activity);
            intent.putExtra("mascota", mascota);
            intent.putExtra("key", mas_key);
            intent.putExtra("1", "primero");
            startActivity(intent);
        }

       // }
        return super.onOptionsItemSelected(item);
    }
}


