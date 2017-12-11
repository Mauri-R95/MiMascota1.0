package com.example.mauri_r95.mimascota10.Modelo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Mauri_R95 on 05-12-2017.
 */

public class Ubicacion implements LocationListener {
    private Context ctx;
    LocationManager locationManager;
    String proveedor;
    Location lc;
    private boolean networkOn;

    public Ubicacion(Context ctx) {
        this.ctx = ctx;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        proveedor = LocationManager.NETWORK_PROVIDER;
        if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        networkOn = locationManager.isProviderEnabled(proveedor);
        locationManager.requestLocationUpdates(proveedor, 1000, 1, this);
        lc = locationManager.getLastKnownLocation(proveedor);
        getLocation();
    }

    /**
     *  Obtener Localización GPS
     */
    private void getLocation() {
        if (networkOn) {
            if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location lc = locationManager.getLastKnownLocation(proveedor);
            if(lc != null){
                //Toast.makeText(ctx, ""+lc.getLatitude()+","+lc.getLongitude(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public double getLatitude(){
        return lc.getLatitude();
    }

    public double getLongitude(){
        return lc.getLongitude();
    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

