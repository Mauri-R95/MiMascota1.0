package com.example.mauri_r95.mimascota10.Vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Usuario;
import com.example.mauri_r95.mimascota10.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase para editar los datos del usuario
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class EditarCuentaActivity extends AppCompatActivity {

    private ImageView imagen_user;
    private ImageButton imagen_up;
    private TextView email, ubicacion;
    private EditText nombre, telefono;
    private LinearLayout la_ubicacion, la_pass;
    private Button guardar_Edit;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String emailS ,key_user, imagen_nom;
    private Usuario usuario = new Usuario();
    private ProgressDialog dialog;
    private StorageReference storage;
    private Boolean state_imagen = false;
    private static final int GALLERY_INT = 1; //CODIGO QUE UTILIZA LA GALERIA CUANDO APRETEMOS EL BOTON PARA ABRIR LA GALERIA, CODIGO QUE MANEJA INTERNAMENTE
    Uri urif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuenta);

        dialog = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance().getReference();

        imagen_user = (ImageView)findViewById(R.id.img_user);
        imagen_up = (ImageButton)findViewById(R.id.img_up_user);
        email = (TextView)findViewById(R.id.email_user);
        ubicacion = (TextView)findViewById(R.id.ubic_edit_user);
        nombre = (EditText)findViewById(R.id.nombre_user);
        telefono = (EditText)findViewById(R.id.tel_user);
        la_ubicacion = (LinearLayout)findViewById(R.id.la_ubic_user);
        la_pass = (LinearLayout)findViewById(R.id.la_pass_user);
        guardar_Edit = (Button)findViewById(R.id.guardar_edit_user);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            usuario = extras.getParcelable("usuario");
            key_user = extras.getString("user");
            if(extras.getString("1").equals("primero")){
                imagen_nom = usuario.getImagen_nom();
            }else{
                imagen_nom = extras.getString("imagen_nom");
            }
            Glide.with(getApplicationContext())
                    .load(usuario.getImagen())
                    .crossFade()
                    .fitCenter()
                    .centerCrop()
                    .into(imagen_user);
            email.setText(usuario.getEmail());
            if(usuario.getComuna().isEmpty() && usuario.getRegion().isEmpty()){
                ubicacion.setText("Ubicación");
            }else{
                ubicacion.setText(usuario.getRegion() + " - " + usuario.getComuna());
            }
            nombre.setText(usuario.getNombre());
            telefono.setText(usuario.getTelefono());

        }

        //imagen
        imagen_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK); //SELECCIONAR UNA IMAGEN DE LA GALERIA
                intent.setType("image/*"); //selecciona todas las imagenes
                startActivityForResult(intent,GALLERY_INT);
            }
        });



        la_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre(nombre.getText().toString());
                usuario.setTelefono(telefono.getText().toString());
                Intent intent = new Intent(EditarCuentaActivity.this, RegionActivity.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("user", key_user);
                intent.putExtra("1", "segundo");
                intent.putExtra("imagen_nom", imagen_nom);
                startActivity(intent);
            }
        });

        la_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre(nombre.getText().toString());
                usuario.setTelefono(telefono.getText().toString());
                Intent intent = new Intent(EditarCuentaActivity.this, CambiarPassActivity.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("user", key_user);
                intent.putExtra("1", "segundo");
                intent.putExtra("imagen_nom", imagen_nom);
                startActivity(intent);

            }
        });

        guardar_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuario.getImagen_nom().equals("sas.png")) {
                    if (!nombre.getText().toString().isEmpty()) {
                        usuario.setNombre(nombre.getText().toString());
                        if (!telefono.getText().toString().isEmpty()) {
                            usuario.setTelefono(telefono.getText().toString());
                            if (!ubicacion.getText().toString().equals("Ubicación")) {
                                dialog.setMessage("Guardando Cambios...");
                                dialog.show();
                                reference.child(FirebaseReference.REF_USUARIOS)
                                        .child(key_user).setValue(usuario);
                                if(!usuario.getImagen_nom().equals("sas.png") || !usuario.getImagen_nom().equals(imagen_nom)){
                                    storage.child("usuarios").child(imagen_nom).delete();
                                }
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Datos cambiados correctamente", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(EditarCuentaActivity.this, MiCuentaActivity.class);
                                intent.putExtra("email", usuario.getEmail());
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "Seleccione Ubicación", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Campo teléfono vacio", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Campo nombre vacio", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Debe seleccion una foto de perfil", Toast.LENGTH_LONG).show();
                }


            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_INT && resultCode == RESULT_OK) {
            urif = data.getData();

            StorageReference filePath = storage.child("usuarios").child(urif.getLastPathSegment());
            //publicar.setOn
            if(!usuario.getImagen_nom().equals("sas.png") && state_imagen && !usuario.getImagen_nom().equals(imagen_nom)){
                storage.child("usuarios").child(usuario.getImagen_nom()).delete();
            }
            filePath.putFile(urif).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    state_imagen = true;
                    Toast.makeText(getApplicationContext(), "Se subio exitosamente", Toast.LENGTH_LONG).show();
                    Uri descargarFoto = taskSnapshot.getDownloadUrl();
                    usuario.setImagen_nom(urif.getLastPathSegment());
                    usuario.setImagen(String.valueOf(descargarFoto));
                    Glide.with(EditarCuentaActivity.this)
                            .load(descargarFoto) //cargar el link
                            .fitCenter() //acomodar la foto
                            .centerCrop() //ajustar foto
                            .into(imagen_user);
                }
            });

        } else {
            Toast.makeText(EditarCuentaActivity.this, "error", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        if(!usuario.getImagen_nom().equals("sas.png") || !usuario.getImagen_nom().equals(imagen_nom)){
            storage.child("usuarios").child(usuario.getImagen_nom()).delete();
        }
        super.onBackPressed();
    }
}

