package com.bernal.jonatan.whip;

public class Fuente {

    String nombre;
    int imagen;
    String contenido;
    int estado;

    public Fuente(String nombre, int imagen, String contenido, int estado) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.contenido = contenido;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
