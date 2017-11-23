package com.example.mauri_r95.mimascota10.Modelos;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mauri_R95 on 27-09-2017.
 */

public class Mascota implements Parcelable {
    String imagen; //
    String imagen_nom;
    String nombre;//
    String fecha; //
    String fecha_nac;
    String comuna; //
    String tipo; //
    String categoria;//
    String sexo; //
    String tamano; //
    String descripcion; //
    String raza;//
    String usuario; //

    public Mascota() {
    }

    public Mascota(String imagen, String imagen_nom, String nombre, String fecha, String fecha_nac, String comuna, String tipo, String categoria, String sexo, String tamano, String descripcion, String raza, String usuario) {
        this.imagen = imagen;
        this.imagen = imagen_nom;
        this.nombre = nombre;
        this.fecha = fecha;
        this.fecha_nac = fecha_nac;
        this.comuna = comuna;
        this.tipo = tipo;
        this.categoria = categoria;
        this.sexo = sexo;
        this.tamano = tamano;
        this.descripcion = descripcion;
        this.raza = raza;
        this.usuario = usuario;
    }


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen_nom() {return imagen_nom;}

    public void setImagen_nom(String imagen_nom) {this.imagen_nom = imagen_nom;}

    public static Creator<Mascota> getCREATOR() {
        return CREATOR;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getUsuario() {return usuario;}

    public void setUsuario(String usuario) {this.usuario = usuario;}


    protected Mascota(Parcel in) {
        imagen = in.readString();
        imagen_nom = in.readString();
        nombre = in.readString();
        fecha = in.readString();
        fecha_nac = in.readString();
        comuna = in.readString();
        tipo = in.readString();
        categoria = in.readString();
        sexo = in.readString();
        tamano = in.readString();
        descripcion = in.readString();
        raza = in.readString();
        usuario = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagen);
        dest.writeString(imagen_nom);
        dest.writeString(nombre);
        dest.writeString(fecha);
        dest.writeString(fecha_nac);
        dest.writeString(comuna);
        dest.writeString(tipo);
        dest.writeString(categoria);
        dest.writeString(sexo);
        dest.writeString(tamano);
        dest.writeString(descripcion);
        dest.writeString(raza);
        dest.writeString(usuario);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Mascota> CREATOR = new Parcelable.Creator<Mascota>() {
        @Override
        public Mascota createFromParcel(Parcel in) {
            return new Mascota(in);
        }

        @Override
        public Mascota[] newArray(int size) {
            return new Mascota[size];
        }
    };
}