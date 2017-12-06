package com.example.mauri_r95.mimascota10.Modelo;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mauri_R95 on 27-09-2017.
 */

public class Mascota implements Parcelable {
    String imagen; //
    String imagenNom;
    String nombre;//
    String fecha; //
    String fechaNac;
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


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagenNom() {return imagenNom;}

    public void setImagenNom(String imagenNom) {this.imagenNom = imagenNom;}

    public static Creator<Mascota> getCREATOR() {
        return CREATOR;
    }

    public String getFechaNac() {return fechaNac;}

    public void setFechaNac(String fechaNac) {this.fechaNac = fechaNac;}

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

    public String getUsuario() {return usuario;}

    public void setUsuario(String usuario) {this.usuario = usuario;}


    protected Mascota(Parcel in) {
        imagen = in.readString();
        imagenNom = in.readString();
        nombre = in.readString();
        fecha = in.readString();
        fechaNac= in.readString();
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
        dest.writeString(imagenNom);
        dest.writeString(nombre);
        dest.writeString(fecha);
        dest.writeString(fechaNac);
        dest.writeString(comuna);
        dest.writeString(tipo);
        dest.writeString(categoria);
        dest.writeString(sexo);
        dest.writeString(tamano);
        dest.writeString(descripcion);
        dest.writeString(raza);
        dest.writeString(usuario);
    }

    /**
     * Metodo para pasar una clase como parametro
     */

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