package com.example.amst_monitoreodepenelessolares;

// con esta clase se obtiene la informacion que se encuentra dentro del grupo paneles para luego
// ser mostrados en la actividad de sensores
public class paneles {

    String posicion;
    String carga;
    String estado;
    String voltaje;


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

    public String getVoltaje() {
        return voltaje;
    }

    public void setVoltaje(String voltaje) {
        this.voltaje = voltaje;
    }
}
