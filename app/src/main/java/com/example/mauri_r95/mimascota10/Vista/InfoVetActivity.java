package com.example.mauri_r95.mimascota10.Vista;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mauri_r95.mimascota10.Modelo.Ubicacion;
import com.example.mauri_r95.mimascota10.Modelo.Veterinario;
import com.example.mauri_r95.mimascota10.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class InfoVetActivity extends AppCompatActivity implements OnMapReadyCallback {

    Ubicacion ub;
    GoogleMap map;
    SupportMapFragment mapFragment;
    Veterinario veterinario;
    TextView nombre,direccion, contacto, pagWeb, email, distancia;
    Button gen_rut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_vet);

        nombre = (TextView)findViewById(R.id.Nombre_vet_text);
        direccion = (TextView)findViewById(R.id.direccion_vet_text);
        contacto = (TextView)findViewById(R.id.contacto_vet_text);
        pagWeb = (TextView)findViewById(R.id.pagWeb_vet_text);
        email = (TextView)findViewById(R.id.Email_vet_text);
        distancia = (TextView)findViewById(R.id.Distancia_text);
        gen_rut = (Button)findViewById(R.id.gen_rut);

        ub = new Ubicacion(this);
        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);
        Bundle extras = getIntent().getExtras();
        veterinario = extras.getParcelable("vet");

        nombre.setText(veterinario.getNombre());
        direccion.setText(veterinario.getDireccion());
        if(veterinario.getTelefono().isEmpty()){
            contacto.setTextColor(R.color.black);
            contacto.setText("Numero no disponible");
        }else{
            contacto.setText(veterinario.getTelefono());
        }

        if(veterinario.getPagweb().isEmpty()){
            pagWeb.setTextColor(R.color.black);
            pagWeb.setText("No dispone de Página Web");
        }else{
            pagWeb.setText(veterinario.getPagweb());
        }

        if(veterinario.getEmail().isEmpty()){
            email.setText("No Dispone de Email");
        }else {
            email.setText(veterinario.getEmail());
        }
        distancia.setText(veterinario.getDistancia() + " m");

        pagWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!veterinario.getPagweb().isEmpty()) {
                    Uri pagWeb = Uri.parse(veterinario.getPagweb());
                    Intent intent = new Intent(Intent.ACTION_VIEW, pagWeb);
                    startActivity(intent);
                }
            }
        });

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri tel = Uri.parse("tel:"+veterinario.getTelefono());
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(tel);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL); //seleccionar que tipo de mapa queremeos
        UiSettings uiSettings = map.getUiSettings(); //configurar mapa
        uiSettings.setZoomControlsEnabled(true); //añade el controlador de zoom
        uiSettings.setMyLocationButtonEnabled(true); //añade el boton de mi localizacion actual
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; //permisos
        }
        map.setMyLocationEnabled(true); //obtener ubicacion actual
        LatLng here = new LatLng(ub.getLatitude(), ub.getLongitude());
        map.addMarker(new MarkerOptions().position(here).title("here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        LatLng punto = new LatLng(Double.parseDouble(veterinario.getLat()), Double.parseDouble(veterinario.getLng()));
        map.addMarker(new MarkerOptions().position(punto).title(veterinario.getNombre())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //posicionar la camara segun el punto
        map.addPolyline(new PolylineOptions().add(here,punto).width(10).color(Color.BLUE)); //genera una linea de un punto a otro
        CameraPosition hereC = CameraPosition.builder().target(here).zoom(14).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(hereC));
    }
}
