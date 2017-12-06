package com.example.mauri_r95.mimascota10.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mauri_R95 on 26-09-2017.
 */

public class Usuario implements Parcelable {

    String nombre;
    String email;
    String telefono;
    String comuna;
    String imagen;
    String imagenNom;
    String region;

    public Usuario() {
    }

    public Usuario(String nombre, String email, String telefono, String comuna, String region, String imagen, String imagenNom) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.comuna = comuna;
        this.region = region;
        this.imagen = imagen;
        this.imagenNom = imagenNom;
    }

    public String getComuna() {return comuna;}

    public void setComuna(String comuna) {this.comuna = comuna;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen;}

    public String getImagenNom() {return imagenNom;}

    public void setImagenNom(String imagenNom) {this.imagenNom = imagenNom;}

    public static Creator<Usuario> getCREATOR() {return CREATOR;}

    public String getRegion() {return region;}

    public void setRegion(String region) {this.region = region;}

    protected Usuario(Parcel in) {
        nombre = in.readString();
        email = in.readString();
        telefono = in.readString();
        comuna = in.readString();
        imagen = in.readString();
        imagenNom = in.readString();
        region = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(email);
        dest.writeString(telefono);
        dest.writeString(comuna);
        dest.writeString(imagen);
        dest.writeString(imagenNom);
        dest.writeString(region);
    }
    /**
     * Metodo para pasar una clase como parametro
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}