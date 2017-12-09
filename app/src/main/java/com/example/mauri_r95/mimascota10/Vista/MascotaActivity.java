package com.example.mauri_r95.mimascota10.Vista;

import android.app.FragmentManager;
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
import com.example.mauri_r95.mimascota10.Modelo.Mascota;
import com.example.mauri_r95.mimascota10.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    ImageView foto;
    TextView nombre, fecha, fecha_nac, comuna, tip_raz, tip_raz_T, categoria, sexo, tamano, desc;
    LinearLayout li_tam;
    private FirebaseDatabase database;
    private String activity, mas_key;
    private Button contacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota);

        //CARGAR OBJETO
        final Bundle extras = getIntent().getExtras();
        activity = extras.getString("activity");
        mascota =  extras.getParcelable("mascota");
        mas_key = extras.getString("key");
        //Toast.makeText(getApplicationContext(), mas_key, Toast.LENGTH_SHORT).show();

        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        foto = (ImageView)findViewById(R.id.imageView_pet);
        nombre = (TextView)findViewById(R.id.nombre_pet);
        fecha = (TextView)findViewById(R.id.fecha_pet);
        fecha_nac = (TextView)findViewById(R.id.fecha_nac);
        comuna = (TextView)findViewById(R.id.text_com_pet);
        tip_raz = (TextView)findViewById(R.id.text_tip_pet);
        tip_raz_T = (TextView)findViewById(R.id.tipo_raza);
        categoria = (TextView)findViewById(R.id.text_cat_pet);
        sexo = (TextView)findViewById(R.id.text_sex_pet);
        tamano = (TextView)findViewById(R.id.text_tam_pet);
        li_tam = (LinearLayout)findViewById(R.id.li_tam_pet);
        asignarVariables(mascota);



    }

    public void asignarVariables(Mascota mascota){
        Glide.with(MascotaActivity.this)
                .load(mascota.getImagen()) //cargar el link
                //.crossFade()
                //.fitCenter() //acomodar la foto
                //.centerCrop() //ajustar foto
                .into(foto);
        nombre.setText(mascota.getNombre());
        fecha.setText(mascota.getFecha());
        fecha_nac.setText("fecha de nacimiento: "+ mascota.getFecha_nac());
        comuna.setText(mascota.getComuna());
        if(mascota.getRaza().isEmpty()){
            tip_raz_T.setText("Tipo");
            tip_raz.setText(mascota.getTipo());
        }else{
            tip_raz_T.setText("Tipo - Raza");
            tip_raz.setText(mascota.getTipo() + " - " + mascota.getRaza());
        }
        categoria.setText(mascota.getCategoria());
        sexo.setText(mascota.getSexo());
        if(mascota.getTamano().isEmpty()){
            li_tam.setVisibility(View.GONE);
        }else{
            tamano.setText(mascota.getTamano());
        }
        desc = (TextView)findViewById(R.id.text_des_pet);
        if(mascota.getDescripcion().isEmpty()){
            desc.setText("Sin Descripción");
        }else{
            desc.setText(mascota.getDescripcion());
        }
    }

    public void mostrarDialog(View v){
        FragmentManager manager = getFragmentManager();
        DialogContactoMas myDialog = new DialogContactoMas();
        myDialog.show(manager, "contacto");
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
                || extras.getString("activity").equals("buscar")
                || extras.getString("activity").equals("tag")) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(activity.equals("tag")){
            Intent intent = new Intent(MascotaActivity.this, EscanearTagActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}


