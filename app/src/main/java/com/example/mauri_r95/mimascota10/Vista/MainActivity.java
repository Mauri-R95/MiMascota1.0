package com.example.mauri_r95.mimascota10.Vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mauri_r95.mimascota10.Adapter;
import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Mascota;
import com.example.mauri_r95.mimascota10.Modelo.Usuario;
import com.example.mauri_r95.mimascota10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase que carga el menu principal
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    String emailS;
    private Adapter adapter;
    private RecyclerView recyclerView;
    private List<Mascota> mMascotas;
    private List<String> mas_key;
    private ProgressDialog dialog;

    private FirebaseDatabase database;
    private DatabaseReference reference;



    // variable para ver el estado de la sesion
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth.AuthStateListener mAuthListenerPA;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        dialog = new ProgressDialog(this);
        setSupportActionBar(toolbar);
        //cargar recyclerView



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.public_anuncio);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                agregarMascota();

            }
        });




        //asigna la vista de los layout
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //para enlazar la vista nav_header_main con la activity y el main, para luego poder asignarlo
        View hview = navigationView.getHeaderView(0);

        //inicializacion de variables y asignarlas a un elemento del layout
        final Button ingresar = (Button)hview.findViewById(R.id.ingresar);
        final TextView emailNav = (TextView)hview.findViewById(R.id.email_nav);
        final TextView nombreNav = (TextView)hview.findViewById(R.id.nom_nav);
        final Button cerrar = (Button)hview.findViewById(R.id.cerrar);
        final ImageView imageUser = (ImageView)hview.findViewById(R.id.ImagenUser);


        navigationView.setNavigationItemSelectedListener(this);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);


            }

        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //imagenUser.setImageDrawable(getResources().getDrawable(R.drawable.logo));
                FirebaseAuth.getInstance().signOut();

            }
        });

        //RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //MainModel model = new MainModel();
        //mascotas = model.inicializarMascotas();
        mMascotas = new ArrayList<>();
        mas_key = new ArrayList<>();
        adapter = new Adapter(mMascotas);
        recyclerView.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();
        reference= database.getReference();
        reference.child(FirebaseReference.ref_mascotas).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMascotas.removeAll(mMascotas);
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String key = ds.getKey();
                    Mascota mascota = ds.getValue(Mascota.class);
                    mas_key.add(key);
                    mMascotas.add(mascota);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //dialog.dismiss();
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mascota mascota = mMascotas.get(recyclerView.getChildAdapterPosition(v));
                int pos = recyclerView.getChildAdapterPosition(v);
                String key = mas_key.get(pos);
                Intent intent = new Intent(MainActivity.this, MascotaActivity.class);
                intent.putExtra("activity", "main");
                intent.putExtra("key", key);
                intent.putExtra("mascota", mascota);
                intent.putExtra("email", emailS);
                startActivity(intent);

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser(); //comprueba si el usuario existe

                if (user != null){
                    database = FirebaseDatabase.getInstance();
                    emailS = user.getEmail();
                    DatabaseReference reference= database.getReference();
                    reference.child(FirebaseReference.ref_usuarios).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Usuario u = ds.getValue(Usuario.class);
                                if(u.getEmail().equals(user.getEmail())){
                                    emailNav.setText(u.getEmail());
                                    nombreNav.setText(u.getNombre());
                                    Glide.with(getApplicationContext())
                                            .load(u.getImagen())
                                            .crossFade()
                                            .fitCenter()
                                            .centerCrop()
                                            .into(imageUser);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //se puede iniciar una activity
                    emailNav.setVisibility(View.VISIBLE);
                    nombreNav.setVisibility(View.VISIBLE);
                    ingresar.setVisibility(View.GONE);
                    cerrar.setVisibility(View.VISIBLE);
                }
                else{

                    Log.i("SESION", "sesion cerrada");
                    emailS = "sin usuario";
                    imageUser.setImageDrawable(getResources().getDrawable(R.drawable.logo));
                    emailNav.setVisibility(View.GONE);
                    nombreNav.setVisibility(View.GONE);
                    ingresar.setVisibility(View.VISIBLE);
                    cerrar.setVisibility(View.GONE);
                }

            }
        };//comprueba si inicia sesion o cierra sesion




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.buscar){
            Intent intent = new Intent(MainActivity.this, BuscarActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.anuncios) {

        } else if (id == R.id.publicar_anuncio) {
            agregarMascota();
        } else if (id == R.id.buscar_vet) {
            Intent intent = new Intent(MainActivity.this, BuscarVetActivity.class);

            startActivity(intent);

        } else if (id == R.id.scan_cod) {
            Intent intent = new Intent(MainActivity.this, EscanearTagActivity.class);
            startActivity(intent);

        } else if (id == R.id.mi_cuenta) {
            miCuenta();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //VERIFICA SI HA INICIADO SESION ANTES DE AGREGAR UNA MASCOTA
    public void agregarMascota(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, PubAnuncioActivity.class);
            intent.putExtra("activity", "main");
            intent.putExtra("1", "primero");
            intent.putExtra("email", emailS);
            startActivity(intent);
        } else{
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Debe Iniciar Sesión", Toast.LENGTH_LONG).show();

        }
    }

    //VERIFICA SI HA INICIADO SESION ANTES DE INGRESAR A MI CUENTA
    public void miCuenta(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Intent intent = new Intent(MainActivity.this, MiCuentaActivity.class);
            intent.putExtra("email", emailS);
            startActivity(intent);
        } else{
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Debe Iniciar Sesión", Toast.LENGTH_LONG).show();

        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        //VERIFICA SI HAY UNA SESIÓN INICIADA AL ABRIR EL PROGRAMA
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }


}
