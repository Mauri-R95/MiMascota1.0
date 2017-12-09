package com.example.mauri_r95.mimascota10.Vista;

import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mauri_r95.mimascota10.AdapterVet;
import com.example.mauri_r95.mimascota10.FirebaseReference;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BuscarVetActivity extends AppCompatActivity implements OnMapReadyCallback, ListVetFragment.OnFragmentInteractionListener {

    Ubicacion ub;
    GoogleMap map;

    RecyclerView recyclerView;
    List<Veterinario> veterinarios;
    SupportMapFragment mapFragment;
    FirebaseDatabase database;
    DatabaseReference reference;
    int distancia = 50000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_vet);

        ub = new Ubicacion(this);
        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);




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
        final LatLng here = new LatLng(ub.getLatitude(), ub.getLongitude());
        //final LatLng here = new LatLng(-33.021469,-71.48585800000001 );
        map.addMarker(new MarkerOptions().position(here).title("here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        veterinarios = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        reference.child(FirebaseReference.ref_veterinarios).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Veterinario veterinario = ds.getValue(Veterinario.class);
                    LatLng puntoVet = new LatLng(Double.parseDouble(veterinario.getLat()), Double.parseDouble(veterinario.getLng()));
                    veterinario.setDistancia(calcularDis(here, puntoVet));
                    if(veterinario.getDistancia() < distancia) {
                        cargarMarker(map, Double.parseDouble(veterinario.getLat()), Double.parseDouble(veterinario.getLng()), veterinario.getNombre(), here);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //posicionar la camara segun el punto
        CameraPosition hereC = CameraPosition.builder().target(here).zoom(10).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(hereC));
    }

    public void cargarMarker(GoogleMap map, double lat, double lng, String nombre, LatLng here){
        //icon para colocar color
        LatLng punto = new LatLng(lat, lng);
        calcularDis(here, punto);
        map.addMarker(new MarkerOptions().position(punto).title(nombre)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

    }


    public  int calcularDis(LatLng here, LatLng other ){
        Location hereL = new Location("here");
        hereL.setLatitude(here.latitude);
        hereL.setLongitude(here.longitude);
        Location otherL = new Location("other");
        otherL.setLatitude(other.latitude);
        otherL.setLongitude(other.longitude);
        Float distance = hereL.distanceTo(otherL);
        int dis = Math.round(distance);
        return dis;

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
