package com.example.mauri_r95.mimascota10.Modelo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Mauri_R95 on 05-12-2017.
 */

public class Veterinario implements Parcelable, Comparable<Veterinario> {

    String nombre; //
    String telefono; //
    String pagweb; //
    String direccion; //
    String email; //
    String lat;//
    String lng;//
    int distancia;

    public Veterinario() {
    }

    public Veterinario(String nombre, String telefono, String pagweb, String direccion, String email, String lat, String lng, int distancia) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.pagweb = pagweb;
        this.direccion = direccion;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
        this.distancia = distancia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPagweb() {
        return pagweb;
    }

    public void setPagweb(String pagweb) {
        this.pagweb = pagweb;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    protected Veterinario(Parcel in) {
        nombre = in.readString();
        telefono = in.readString();
        pagweb = in.readString();
        direccion = in.readString();
        email = in.readString();
        lat = in.readString();
        lng = in.readString();
        distancia = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(telefono);
        dest.writeString(pagweb);
        dest.writeString(direccion);
        dest.writeString(email);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeInt(distancia);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Veterinario> CREATOR = new Parcelable.Creator<Veterinario>() {
        @Override
        public Veterinario createFromParcel(Parcel in) {
            return new Veterinario(in);
        }

        @Override
        public Veterinario[] newArray(int size) {
            return new Veterinario[size];
        }
    };


    @Override
    public int compareTo(@NonNull Veterinario o) {
        if(this.distancia < o.getDistancia()){
            return -1;
        }else if(this.distancia > o.getDistancia()){
            return 1;
        }
        return 0;
    }
}

