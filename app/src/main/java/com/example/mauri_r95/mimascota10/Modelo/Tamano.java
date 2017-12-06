package com.example.mauri_r95.mimascota10.Modelo;

/**
 * Created by Mauri_R95 on 04-10-2017.
 */

public class Tamano {
    String imagen;
    String nombre;
    String abrev;
    String peso;

    public Tamano() {
    }

    public Tamano(String imagen, String nombre, String abrev, String peso) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.abrev = abrev;
        this.peso = peso;
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

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}
