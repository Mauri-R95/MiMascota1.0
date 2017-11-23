package com.example.mauri_r95.mimascota10;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase para iniciar sesion
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    protected Button btn_ingresar, btn_crear_cuenta;
    protected EditText emailEdit, passEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_ingresar = (Button) findViewById(R.id.btn_ingresar);
        btn_crear_cuenta = (Button) findViewById(R.id.btn_crear_cuenta);
        emailEdit = (EditText) findViewById(R.id.email_ing);
        passEdit = (EditText) findViewById(R.id.pass_ing);

        //inicializa una instancion de auth
        mAuth = FirebaseAuth.getInstance();


        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "", pass= "";
                email = emailEdit.getText().toString();
                pass = passEdit.getText().toString();
                if (!email.isEmpty() || !pass.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Sesión iniciada correctamente", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_LONG).show();
                }
            }

        });
        btn_crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(intent1);

            }
        });


    }

}