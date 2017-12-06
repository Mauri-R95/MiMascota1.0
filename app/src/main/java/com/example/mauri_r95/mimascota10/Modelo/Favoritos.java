package com.example.mauri_r95.mimascota10.Modelo;

/**
 * Created by Mauri_R95 on 06-11-2017.
 */

public class Favoritos {
    String usuario;
    String mascota;

    public Favoritos() {
    }

    public Favoritos(String usuario, String mascota) {
        this.usuario = usuario;
        this.mascota = mascota;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota; }
}
