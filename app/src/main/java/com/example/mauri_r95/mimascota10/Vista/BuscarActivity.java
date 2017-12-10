package com.example.mauri_r95.mimascota10.Vista;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mauri_r95.mimascota10.R;

/**
 * Created by Mauri_R95 on 04-10-2017.
 * Clase para buscar una mascota segun caracteristicas especificas
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class BuscarActivity extends AppCompatActivity{

    private TextView categoria, tip_mas, raza, raza_tex, tamano, comuna;
    private Switch sexo_s;
    private ToggleButton sexo;
    private Button buscar;
    private LinearLayout li_cat, li_tip_mas, li_raza, li_tam, li_com;
    private View v_raza, v_tam;
    private Boolean statesex = true;
    private String categoria_s, tip_mas_s, raza_s, tamano_s, comuna_s, sexo_st, tamanoS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        categoria = (TextView)findViewById(R.id.categ_b);
        tip_mas = (TextView)findViewById(R.id.tip_masc_b);
        raza = (TextView)findViewById(R.id.Raza_b);
        raza_tex = (TextView)findViewById(R.id.raza_tex_b);
        tamano = (TextView)findViewById(R.id.tamano_b);
        comuna = (TextView)findViewById(R.id.comuna_b);
        sexo_s = (Switch)findViewById(R.id.switch_sexo_b);
        sexo = (ToggleButton)findViewById(R.id.sexo_bus);
        buscar = (Button)findViewById(R.id.btn_buscar);
        li_cat = (LinearLayout)findViewById(R.id.li_categ_b);
        li_tip_mas = (LinearLayout)findViewById(R.id.li_tip_b);
        li_raza = (LinearLayout)findViewById(R.id.li_raza_b);
        li_tam = (LinearLayout)findViewById(R.id.li_tam_b);
        //li_edad = (LinearLayout)findViewById(R.id.li_edad_b);
        li_com = (LinearLayout)findViewById(R.id.li_com_b);
        v_raza = findViewById(R.id.v_raza_b);
        v_tam = findViewById(R.id.v_tam_b);

        tamanoS = getResources().getString(R.string.tamano);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            categoria.setText(extras.getString("categoria"));
            sexo_s.setChecked(extras.getBoolean("sexo_s"));
            sexo.setChecked(extras.getBoolean("sexo"));
            tip_mas.setText(extras.getString("tipo"));
            raza.setText(extras.getString("raza"));
            tamano.setText(extras.getString("tamano"));
            comuna.setText(extras.getString("comuna"));
        }

        //VISUALIZAR O ESCONDER SEGUN TIPO DE MASCOTA
        if (tip_mas.getText().toString().equals("Perro")){
            li_tam.setVisibility(View.VISIBLE);
            v_tam.setVisibility(View.VISIBLE);
            li_raza.setVisibility(View.VISIBLE);
            v_raza.setVisibility(View.VISIBLE);
            raza_tex.setText(R.string.raza);

        }else if (tip_mas.getText().toString().equals("Ave")){
            li_tam.setVisibility(View.GONE);
            v_tam.setVisibility(View.GONE);
            raza_tex.setText(R.string.tipo_de_ave);
            li_raza.setVisibility(View.VISIBLE);
            v_raza.setVisibility(View.VISIBLE);
        }else{
            li_tam.setVisibility(View.GONE);
            v_tam.setVisibility(View.GONE);
            li_raza.setVisibility(View.GONE);
            v_raza.setVisibility(View.GONE);

        }
        //Categoria
        li_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarParametros(CategoriaActivity.class);
            }
        });
        //Tipo de mascota
        li_tip_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarParametros(TipoMascotaActivity.class);
            }
        });
        //Raza
        li_raza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarParametros(RazaActivity.class);
            }
        });
        //Tamaño
        li_tam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarParametros(TamanoActivity.class);
            }
        });
        //Comuna
        li_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarParametros(RegionActivity.class);
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarParametros(MisActivity.class);
            }
        });

    }

    public void pasarParametros(Class activityClass){
        Intent intent = new Intent( BuscarActivity.this, activityClass);
        intent.putExtra("categoria", categoria.getText().toString());
        intent.putExtra("sexo_s", sexo_s.isChecked());
        intent.putExtra("sexo", sexo.isChecked());
        intent.putExtra("tipo", tip_mas.getText().toString());
        intent.putExtra("raza", raza.getText().toString());
        intent.putExtra("tamano", tamano.getText().toString());
        //intent.putExtra("edad",edad.getText().toString());
        intent.putExtra("comuna", comuna.getText().toString());
        intent.putExtra("activity", "buscar");
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mascota, menu);
        menu.getItem(0).setTitle("LIMPIAR");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.editar_mascota){
            categoria.setText(R.string.categoria);
            sexo_s.setChecked(false);
            sexo.setChecked(false);
            tip_mas.setText(R.string.tipo_mascota);
            li_tam.setVisibility(View.GONE);
            v_tam.setVisibility(View.GONE);
            li_raza.setVisibility(View.GONE);
            v_raza.setVisibility(View.GONE);
            raza_tex.setText(R.string.raza);
            raza.setText(R.string.raza);
            tamano.setText(tamanoS);
            comuna.setText(R.string.comuna);



        }
        return super.onOptionsItemSelected(item);

    }
}
