package com.example.amst_monitoreodepenelessolares;

public class paneles {

    String posicion;
    String carga;
    String estado;

    public paneles() {
    }

    public paneles(String posicion, String carga, String estado) {
        this.posicion = posicion;
        this.carga = carga;
        this.estado = estado;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
