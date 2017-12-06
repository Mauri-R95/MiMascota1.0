package com.example.mauri_r95.mimascota10.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Usuario;
import com.example.mauri_r95.mimascota10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase para registrar a un usuario
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class RegistrarActivity extends AppCompatActivity {

    EditText eMail, pass, passR;
    Button btnReg;
    Switch aceptar;
    String passS, eMailS, passRS;
    Usuario usuario = new Usuario();


    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        eMail = (EditText)findViewById(R.id.email_crear);
        pass = (EditText)findViewById(R.id.pass_crear);
        passR = (EditText)findViewById(R.id.rep_pass_crear);
        aceptar = (Switch)findViewById(R.id.switch1);
        btnReg = (Button)findViewById(R.id.btn_reg);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        StorageReference mStorage = FirebaseStorage.getInstance().getReference();




        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eMailS = eMail.getText().toString();
                passS = pass.getText().toString();
                passRS = passR.getText().toString();
                final String [] nombre = eMailS.split("@");



                if (aceptar.isChecked()) {
                    if (!eMailS.isEmpty() || !passS.isEmpty() || !passRS.isEmpty()) {
                        if (!passS.equals(passRS)) {
                            Toast.makeText(getApplicationContext(), "Las contraseñas son distintas", Toast.LENGTH_LONG).show();
                        } else {

                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(eMailS, passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        usuario = new Usuario(nombre[0],eMailS,"","","", "https://firebasestorage.googleapis.com/v0/b/mi-mascota-a6900.appspot.com/o/sas.png?alt=media&token=e6d94bf0-b2b3-4201-a68d-358993887c26", "sas.png");
                                        reference.child(FirebaseReference.ref_usuarios).push().setValue(usuario);
                                        Intent intent = new Intent(RegistrarActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Sesión registrada correctamente", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Debe aceptar los terminos y condiciones", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
