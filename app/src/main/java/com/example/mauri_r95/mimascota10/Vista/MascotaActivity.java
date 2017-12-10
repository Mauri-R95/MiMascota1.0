package com.example.mauri_r95.mimascota10.Vista;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 * clase que carga la informacion de la mascota
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class MascotaActivity extends AppCompatActivity  {

    Mascota mascota;
    Usuario user;
    ImageView foto;
    TextView nombre, fecha, fecha_nac, comuna, tip_raz, tip_raz_T, categoria, sexo, tamano, desc;
    LinearLayout li_tam;
    private String activity, mas_key, email;
    private Button contacto;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    TextView nombre_user, email_user, tel_user;
    String nombre_u, email_u, tel_u;
    Button llamar;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
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
        reference = database.getReference();
        final DatabaseReference reference = database.getReference();
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
        contacto = (Button)findViewById(R.id.btn_contac_mas);

        asignarVariables(mascota);

        reference.child(FirebaseReference.ref_usuarios).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Usuario usuario = ds.getValue(Usuario.class);
                    if(usuario.getEmail().equals(mascota.getUsuario())){
                        nombre_u = usuario.getNombre();
                        email_u = usuario.getEmail();
                        if(!usuario.getTelefono().isEmpty()) {
                            tel_u = usuario.getTelefono();
                        }else{
                            tel_u = "No Disponible";
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(!activity.equals("main") && !activity.equals("tag")){
            contacto.setVisibility(View.GONE);
        }

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telefono;
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MascotaActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_contacto_mas, null);
                TextView nombre_user = (TextView)mView.findViewById(R.id.con_nom_user);
                TextView email_user = (TextView) mView.findViewById(R.id.con_email_user);
                final TextView tel_user = (TextView)mView.findViewById(R.id.con_tel_user);
                Button llamar = (Button)mView.findViewById(R.id.con_btn_call);

                nombre_user.setText("Nombre: "+nombre_u);
                email_user.setText("Email: "+email_u);
                tel_user.setText("Teléfono: "+ tel_u);

                llamar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!tel_u.equals("No Disponible")) {
                            Uri tel = Uri.parse("tel:"+tel_user);
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(tel);
                            int permissionCheck = ContextCompat.checkSelfPermission(
                                    getApplicationContext(), Manifest.permission.CALL_PHONE);
                            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                Log.i("Mensaje", "No se tiene permiso para realizar llamadas telefónicas.");
                                ActivityCompat.requestPermissions( MascotaActivity.this , new String[]{Manifest.permission.CALL_PHONE}, 225);
                            } else {
                                Log.i("Mensaje", "Se tiene permiso para realizar llamadas!");
                            }
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                    // Se tiene permiso
                                }else{
                                    ActivityCompat.requestPermissions(MascotaActivity.this,new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
                                    return;
                                }
                            }else{
                                // No se necesita requerir permiso OS menos a 6.0.
                            }
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Telefono no Disponible", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // El usuario acepto los permisos.
                    Toast.makeText(this, "Gracias, aceptaste los permisos requeridos para el correcto funcionamiento de esta aplicación.", Toast.LENGTH_SHORT).show();
                }else{
                    // Permiso denegado.
                    Toast.makeText(this, "No se aceptó permisos", Toast.LENGTH_SHORT).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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


