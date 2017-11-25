package com.example.mauri_r95.mimascota10;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mauri_r95.mimascota10.Modelos.Mascota;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase que carga un formulario para subir una mascota a la base de datos de la aplicacion
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class PubAnuncioActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView foto;
    private ImageButton foto_up;
    private TextView categoria, tamano, tip_mas, comuna, raza, raza_tex, fecha_nac;
    private Button macho, hembra, publicar;
    private LinearLayout li_cat, li_tam, li_tip, li_com, li_raza, li_nac;
    EditText desc, nombre;
    View view_tam, view_raza;

    private String userS, mas_key, cat_get, activity, imagen_nom_res;
    private StorageReference mStorage;
    private static final int GALLERY_INT = 1; //CODIGO QUE UTILIZA LA GALERIA CUANDO APRETEMOS EL BOTON PARA ABRIR LA GALERIA, CODIGO QUE MANEJA INTERNAMENTE
    private boolean state_nac = false, stateActivity, stateCambio = false, state_imagen = false;
    //private ProgressDialog mProgressDialog;
    private FirebaseDatabase database;
    Mascota mascota = new Mascota();
    Uri urif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_anuncio);
        mStorage = FirebaseStorage.getInstance().getReference();


        //inicializar usuario
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userS = user.getEmail();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();


        foto = (ImageView) findViewById(R.id.img_mas);
        foto_up = (ImageButton) findViewById(R.id.img_up_mas);
        categoria = (TextView) findViewById(R.id.categ);
        tamano = (TextView) findViewById(R.id.tamano_publ);
        tip_mas = (TextView) findViewById(R.id.tip_masc);
        comuna = (TextView) findViewById(R.id.comuna_publ);
        raza = (TextView) findViewById(R.id.Raza_pub);
        raza_tex = (TextView) findViewById(R.id.raza_tex);
        fecha_nac = (TextView) findViewById(R.id.nac_pub);
        nombre = (EditText) findViewById(R.id.nombre_eT);
        desc = (EditText) findViewById(R.id.desc_eT);
        macho = (Button) findViewById(R.id.macho);
        hembra = (Button) findViewById(R.id.hembra);
        publicar = (Button) findViewById(R.id.publicar);
        li_cat = (LinearLayout) findViewById(R.id.la_categ);
        li_nac = (LinearLayout) findViewById(R.id.la_nac);
        li_tam = (LinearLayout) findViewById(R.id.la_tam);
        li_tip = (LinearLayout) findViewById(R.id.la_tip);
        li_com = (LinearLayout) findViewById(R.id.la_com);
        li_raza = (LinearLayout) findViewById(R.id.la_raza);
        view_tam = (View) findViewById(R.id.view_tam_pub);
        view_raza = (View) findViewById(R.id.view_raza_pub);


        //GUARDAR VARIABLES DE LAS OTRAS ACTIVIDADES
        final Bundle extras = getIntent().getExtras();


        if (extras != null) {
            activity = extras.getString("activity");
            if (!extras.getString("activity").equals("main") || extras.getString("1").equals("segundo")) {
                mas_key = extras.getString("key");
                mascota = extras.getParcelable("mascota");
                if (!mascota.getImagen().isEmpty()) {
                    Glide.with(PubAnuncioActivity.this)
                            .load(mascota.getImagen()) //cargar el link
                            //.crossFade()
                            //.fitCenter() //acomodar la foto
                            //.centerCrop() //ajustar foto
                            .into(foto);
                }
                mascota.getImagenNom();
                nombre.setText(mascota.getNombre());
                categoria.setText(mascota.getCategoria());
                if (mascota.getSexo().equals("Macho")) {
                    getSexo(true);
                } else {
                    getSexo(false);
                }
                tip_mas.setText(mascota.getTipo());
                fecha_nac.setText(mascota.getFechaNac());

                raza.setText(mascota.getRaza());
                tamano.setText(mascota.getTamano());
                comuna.setText(mascota.getComuna());
                desc.setText(mascota.getDescripcion());
                if (extras.getString("1").equals("primero")) {
                    cat_get = mascota.getCategoria();
                    imagen_nom_res = mascota.getImagenNom();
                } else {
                    cat_get = extras.getString("cat_get");
                    imagen_nom_res = extras.getString("imagen_nom");
                }
                publicar.setText("Guardar Cambios");

            } else {
                mascota.setImagen("");
                mascota.setImagenNom("");
                mascota.setNombre("");
                mascota.setCategoria(categoria.getText().toString());
                mascota.setSexo(macho.getText().toString());
                mascota.setTipo(tip_mas.getText().toString());
                mascota.setFechaNac(fecha_nac.getText().toString());
                mascota.setRaza(raza.getText().toString());
                mascota.setTamano(tamano.getText().toString());
                mascota.setComuna(comuna.getText().toString());
                mascota.setUsuario(extras.getString("email"));
                mascota.setDescripcion("");
                cat_get = "";
            }
        }

        //VISUALIZAR O ESCONDER SEGUN TIPO DE MASCOTA
        if (mascota.getTipo().equals("Perro")) {
            li_tam.setVisibility(View.VISIBLE);
            view_tam.setVisibility(View.VISIBLE);
            li_raza.setVisibility(View.VISIBLE);
            view_raza.setVisibility(View.VISIBLE);
            raza_tex.setText("Raza");


        } else if (mascota.getTipo().equals("Ave")) {
            li_tam.setVisibility(View.GONE);
            view_tam.setVisibility(View.GONE);
            li_raza.setVisibility(View.VISIBLE);
            view_raza.setVisibility(View.VISIBLE);
            raza_tex.setText("Tipo de Ave");
        } else {
            li_tam.setVisibility(View.GONE);
            view_tam.setVisibility(View.GONE);
            li_raza.setVisibility(View.GONE);
            view_raza.setVisibility(View.GONE);

        }


        //imagen
        foto_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK); //SELECCIONAR UNA IMAGEN DE LA GALERIA
                intent.setType("image/*"); //selecciona todas las imagenes
                startActivityForResult(intent, GALLERY_INT);
            }
        });


        //CATEGORIA
        li_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mascota.setNombre(nombre.getText().toString());
                mascota.setDescripcion(desc.getText().toString());
                Intent intent = new Intent(PubAnuncioActivity.this, CategoriaActivity.class);
                intent.putExtra("1", "segundo");
                intent.putExtra("mascota", mascota);
                intent.putExtra("key", mas_key);
                intent.putExtra("activity", activity);
                intent.putExtra("imagen_nom", imagen_nom_res);
                intent.putExtra("cat_get", cat_get);
                startActivity(intent);
            }
        });


        //SEXO
        macho.setOnClickListener(this);

        hembra.setOnClickListener(this);
        //Toast.makeText(getApplicationContext(), String.valueOf(statesex), Toast.LENGTH_SHORT).show();

        //TIPO MASCOTA
        li_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mascota.setNombre(nombre.getText().toString());
                mascota.setDescripcion(desc.getText().toString());
                Intent intent = new Intent(PubAnuncioActivity.this, TipoMascotaActivity.class);
                intent.putExtra("1", "segundo");
                intent.putExtra("mascota", mascota);
                intent.putExtra("key", mas_key);
                intent.putExtra("activity", activity);
                intent.putExtra("imagen_nom", imagen_nom_res);
                intent.putExtra("cat_get", cat_get);
                startActivity(intent);
            }
        });

        //FECHA DE NACIMIENTO
        li_nac.setOnClickListener(this);

        //RAZA
        li_raza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mascota.setNombre(nombre.getText().toString());
                mascota.setDescripcion(desc.getText().toString());
                Intent intent = new Intent(PubAnuncioActivity.this, RazaActivity.class);
                intent.putExtra("1", "segundo");
                intent.putExtra("mascota", mascota);
                intent.putExtra("key", mas_key);
                intent.putExtra("activity", activity);
                intent.putExtra("imagen_nom", imagen_nom_res);
                intent.putExtra("cat_get", cat_get);
                startActivity(intent);
            }
        });

        //TAMAÑO
        li_tam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mascota.setNombre(nombre.getText().toString());
                mascota.setDescripcion(desc.getText().toString());
                Intent intent = new Intent(PubAnuncioActivity.this, TamanoActivity.class);
                intent.putExtra("1", "segundo");
                intent.putExtra("mascota", mascota);
                intent.putExtra("key", mas_key);
                intent.putExtra("activity", activity);
                intent.putExtra("imagen_nom", imagen_nom_res);
                intent.putExtra("cat_get", cat_get);
                startActivity(intent);

            }
        });

        //COMUNA
        li_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mascota.setNombre(nombre.getText().toString());
                mascota.setDescripcion(desc.getText().toString());
                Intent intent = new Intent(PubAnuncioActivity.this, RegionActivity.class);
                intent.putExtra("1", "segundo");
                intent.putExtra("mascota", mascota);
                intent.putExtra("key", mas_key);
                intent.putExtra("activity", activity);
                intent.putExtra("imagen_nom", imagen_nom_res);
                intent.putExtra("cat_get", cat_get);
                startActivity(intent);
            }
        });

        //Toast.makeText(getApplicationContext(), mascota.getFecha(),Toast.LENGTH_SHORT).show();


        //PUBLICAR
        publicar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("URI", String.valueOf(urif));

                //setear fecha de publicacion
                Calendar c = new GregorianCalendar(TimeZone.GMT_ZONE);
                int ano = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH) + 1;
                int dia = c.get(Calendar.DAY_OF_MONTH);
                mascota.setFecha(dia + "/" + mes + "/" + ano);
                //IMAGEN
                if (!mascota.getImagen().isEmpty()) {
                    mascota.setNombre(nombre.getText().toString());
                    //NOMBRE
                    if (!mascota.getNombre().isEmpty()) {
                        //CATEGORIA
                        if (!mascota.getCategoria().equals("Categoria")) {
                            //SEXO
                            //TIPO DE MASCOTA
                            if (!mascota.getTipo().equals("Tipo de Mascota")) {
                                //RAZA
                                if (li_raza.getVisibility() == View.GONE) {
                                    mascota.setRaza("");
                                }
                                if(!mascota.getRaza().equals("Raza") && !mascota.getRaza().equals("Tipo de Ave")){
                                    //TAMAÑO
                                    if(li_tam.getVisibility() == View.GONE){
                                        mascota.setTamano("");
                                    }

                                    if (!mascota.getTamano().equals("Tamaño")) {

                                        //FECHA DE NACIMIENTO
                                        if (mascota.getFechaNac().equals("Fecha de nacimiento")) {
                                            mascota.setFechaNac("");
                                        }
                                        //COMUNA
                                        if (!mascota.getComuna().equals("Comuna")) {
                                            //Descripcion
                                            mascota.setDescripcion(desc.getText().toString());
                                            //Usuario
                                            mascota.setUsuario(userS);

                                            DatabaseReference reference = database.getReference();
                                            if (!mascota.getCategoria().equals("Mis Mascotas")) {
                                                if (!activity.equals("main")) {
                                                    stateActivity = true;
                                                    if (!cat_get.equals("Mis Mascotas")) {
                                                        reference.child(FirebaseReference.ref_mascotas).child(mas_key).setValue(mascota);
                                                    } else {
                                                        stateCambio = true;
                                                        reference.child(FirebaseReference.ref_mascotas).push().setValue(mascota);
                                                        reference.child(FirebaseReference.ref_mis_mascotas).child(mas_key).removeValue();
                                                    }

                                                } else {
                                                    stateActivity = false;
                                                    reference.child(FirebaseReference.ref_mascotas).push().setValue(mascota);
                                                }
                                            } else {
                                                if (!activity.equals("main")) {
                                                    stateActivity = true;
                                                    if (cat_get.equals("Mis Mascotas")) {
                                                        reference.child(FirebaseReference.ref_mis_mascotas).child(mas_key).setValue(mascota);
                                                    } else {
                                                        stateCambio = true;
                                                        reference.child(FirebaseReference.ref_mis_mascotas).push().setValue(mascota);
                                                        reference.child(FirebaseReference.ref_mascotas).child(mas_key).removeValue();
                                                    }
                                                } else {
                                                    stateActivity = false;
                                                    reference.child(FirebaseReference.ref_mis_mascotas).push().setValue(mascota);
                                                }


                                            }
                                            //if(!imagen.equals(imagen_nom_res) && !imagen_nom.equals(imagen_nom_res)) {

                                            if (stateActivity) {
                                                Toast.makeText(PubAnuncioActivity.this, "Cambios guardados correctamente", Toast.LENGTH_LONG).show();
                                                if (stateCambio) {
                                                    Intent intent = new Intent(PubAnuncioActivity.this, MiCuentaActivity.class);
                                                    intent.putExtra("email", userS);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    if (mascota.getCategoria().equals("Mis Mascotas")) {
                                                        Toast.makeText(PubAnuncioActivity.this, "Mascota fue cambiada a Mis Mascotas", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(PubAnuncioActivity.this, "Mascota fue cambiada a Mis Publicaciones", Toast.LENGTH_LONG).show();
                                                    }
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Intent intent1 = new Intent(PubAnuncioActivity.this, MascotaActivity.class);
                                                    intent1.putExtra("email", userS);
                                                    intent1.putExtra("activity", activity);
                                                    intent1.putExtra("mascota", mascota);
                                                    intent1.putExtra("key", mas_key);
                                                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent1);
                                                    finish();
                                                }
                                            } else {
                                                Intent intent = new Intent(PubAnuncioActivity.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                Toast.makeText(PubAnuncioActivity.this, "Mascota Ingresada Correctamente", Toast.LENGTH_LONG).show();
                                                startActivity(intent);
                                                finish();
                                            }

                                        } else {
                                            Toast.makeText(PubAnuncioActivity.this, "Seleccione Comuna", Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(PubAnuncioActivity.this, "Seleccione Tamaño", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    //if(!mascota.getRaza().equals("Raza") || mascota.getRaza().equals("Tipo de Ave")){
                                    if(mascota.getRaza().equals("Raza")){
                                        Toast.makeText(PubAnuncioActivity.this, "Seleccione Raza", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(PubAnuncioActivity.this, "Seleccione Tipo de ave", Toast.LENGTH_LONG).show();
                                    }
                                }
                            } else {
                                Toast.makeText(PubAnuncioActivity.this, "Seleccione Tipo de mascota", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(PubAnuncioActivity.this, "Seleccione Categoria", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(PubAnuncioActivity.this, "Sin Nombre", Toast.LENGTH_LONG).show();
                    }
            }else{
                Toast.makeText(PubAnuncioActivity.this, "Sin Imagen", Toast.LENGTH_LONG).show();
            }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_INT && resultCode == RESULT_OK) {
            urif = data.getData();
            StorageReference filePath = mStorage.child("mascotas").child(urif.getLastPathSegment());
            if(!activity.equals("main")){
                if(!mascota.getImagenNom().isEmpty()){
                    mStorage.child("mascotas").child(mascota.getImagenNom()).delete();
                }
            }else{
                if(!mascota.getImagenNom().isEmpty() && state_imagen){
                    mStorage.child("mascotas").child(mascota.getImagenNom()).delete();
                }
            }
            filePath.putFile(urif).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    state_imagen = true;
                    Toast.makeText(PubAnuncioActivity.this, "Se subio exitosamente", Toast.LENGTH_LONG).show();
                    Uri descargarFoto = taskSnapshot.getDownloadUrl();
                    mascota.setImagenNom(urif.getLastPathSegment());
                    mascota.setImagen(String.valueOf(descargarFoto));
                    Glide.with(PubAnuncioActivity.this)
                            .load(descargarFoto) //cargar el link
                            .fitCenter() //acomodar la foto
                            .centerCrop() //ajustar foto
                            .into(foto);
                }
            });

        } else {
            Toast.makeText(PubAnuncioActivity.this, "error", Toast.LENGTH_LONG).show();
        }
    }



    public void getSexo (Boolean state){
        if(state) {
            macho.setBackground(getDrawable(R.drawable.boton_redondo));
            macho.setTextColor(Color.BLACK);
            hembra.setBackground(getDrawable(R.drawable.boton_red_gris));
            hembra.setTextColor(Color.WHITE);
            mascota.setSexo(macho.getText().toString());
        }else {
            macho.setBackground(getDrawable(R.drawable.boton_red_gris));
            macho.setTextColor(Color.WHITE);
            hembra.setBackground(getDrawable(R.drawable.boton_redondo));
            hembra.setTextColor(Color.BLACK);
            mascota.setSexo(hembra.getText().toString());
        }

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_user){

        }
        else if(v.getId() == R.id.macho){
            getSexo(true);
        }
        else if(v.getId() == R.id.hembra){
            getSexo(false);
        }
        else if (v.getId() == R.id.la_nac){
            Calendar c = new GregorianCalendar(TimeZone.GMT_ZONE);
            final int ano = c.get(Calendar.YEAR);
            final int mes = c.get(Calendar.MONTH)+1;
            final int dia = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if(year == ano){
                        if(month+1 == mes){
                            if(dayOfMonth <= dia){
                                fecha_nac.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                                state_nac = false;
                            }else{
                                fecha_nac.setText(dia + "/" + mes + "/" + ano);
                                state_nac = true;
                            }
                        }else if (month < mes){
                            fecha_nac.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                            state_nac = false;
                        }else{
                            fecha_nac.setText(dia + "/" + mes + "/" + ano);
                            state_nac = true;
                        }
                    }else if (year < ano){
                        fecha_nac.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        state_nac = false;
                    }else{
                        fecha_nac.setText(dia + "/" + mes + "/" + ano);
                        state_nac = true;
                    }
                    if(state_nac){
                        Toast.makeText(PubAnuncioActivity.this, "Fecha supera a la actual", Toast.LENGTH_LONG).show();
                    }
                        mascota.setFechaNac(fecha_nac.getText().toString());

                }
            }
            ,dia,mes,ano);
            datePickerDialog.show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_modificar_mascota, menu);
        if(extras.getString("activity").equals("main")) {
            menu.getItem(0).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.eliminar_mascota){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("¿Esta seguro que desea eliminar mascota?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference();
                            mStorage.child("mascotas").child(mascota.getImagenNom()).delete();
                            if(activity.equals("Mis Mascotas")){
                                reference.child(FirebaseReference.ref_mis_mascotas).child(mas_key).removeValue();
                            }else{
                                reference.child(FirebaseReference.ref_mascotas).child(mas_key).removeValue();
                            }
                            Intent intent = new Intent(PubAnuncioActivity.this, MisActivity.class);
                            intent.putExtra("email", userS);
                            intent.putExtra("activity", activity);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Mascota eliminada de " + activity , Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Eliminar Mascota");
            alertDialog.show();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();
        if(!activity.equals("main")){
            if(!mascota.getImagenNom().isEmpty()){
                mStorage.child("mascotas").child(mascota.getImagenNom()).delete();
            }
        }else{
            if(state_imagen){
                mStorage.child("mascotas").child(mascota.getImagenNom()).delete();
            }
        }
        super.onBackPressed();
    }
}
