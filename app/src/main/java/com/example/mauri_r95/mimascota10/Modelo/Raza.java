package com.example.mauri_r95.mimascota10.Modelo;

/**
 * Created by Mauri_R95 on 04-10-2017.
 */

public class Raza {
    String imagen;
    String nombre;


    public Raza() {
    }

    public Raza(String imagen, String nombre) {
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
