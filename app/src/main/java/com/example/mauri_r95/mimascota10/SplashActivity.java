package com.example.mauri_r95.mimascota10;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase que carga el inicio  y el logo de la aplicacion
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 **/

public class SplashActivity extends Activity{

    private ImageView imageView;
    private String logo = "https://firebasestorage.googleapis.com/v0/b/mi-mascota-a6900.appspot.com/o/logo.png?alt=media&token=0d5db17d-9dc9-43ef-b7ac-090cf1a2629e";
    private int time_spash = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView)findViewById(R.id.logo);
        Glide.with(SplashActivity.this)
                .load(logo) //cargar el link
                 //acomodar la foto
                 //ajustar foto
                .into(imageView);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },time_spash);
    }
}
