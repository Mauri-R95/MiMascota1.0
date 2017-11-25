package com.example.mauri_r95.mimascota10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mauri_r95.mimascota10.Modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class CambiarPassActivity extends AppCompatActivity {

    private EditText pass,passR;
    private Button confirmar;
    private String passS, passRS;
    ProgressDialog dialog;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);


        pass = (EditText)findViewById(R.id.pass_change);
        passR = (EditText)findViewById(R.id.pass_rep_change);
        confirmar = (Button)findViewById(R.id.confirmar_change);
        dialog = new ProgressDialog(this);
        auth =FirebaseAuth.getInstance();

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passS = pass.getText().toString();
                passRS = passR.getText().toString();
                if(!passS.isEmpty() || !passRS.isEmpty()){
                    if(passRS.equals(passS)){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user != null){
                            Bundle extras = getIntent().getExtras();
                            final Usuario usuario = extras.getParcelable("usuario");
                            dialog.setMessage("Cambiando contraseña, espere por favor!");
                            dialog.show();

                            user.updatePassword(passS)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Clave cambiada exitosamente",Toast.LENGTH_SHORT).show();
                                            auth.signOut();
                                            Intent intent = new Intent(CambiarPassActivity.this, EditarCuentaActivity.class);
                                            intent.putExtra("usuario", usuario);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Las contraseñas son distintas", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
