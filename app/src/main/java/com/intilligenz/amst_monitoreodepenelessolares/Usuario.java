package com.intilligenz.amst_monitoreodepenelessolares;

// Con esta clase se obtiene la información dentro del valor Usuario
public class Usuario {

    String correo;
    String nombre;
    paneles panelesx;

    public Usuario() {
    }

    public Usuario(String correo, String nombre, paneles panelesx) {
        this.correo = correo;
        this.nombre = nombre;
        this.panelesx = panelesx;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public paneles getPaneles() {
        return panelesx;
    }

    public void setPaneles(paneles paneles) {
        this.panelesx = paneles;
    }
}
