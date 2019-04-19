package com.bernal.jonatan.whip;

public class Fuente {


    String nombre;
    String imagen;
    String contenido;
    int estado;
    String id;
    String type;



    public Fuente(String id, String nombre, String imagen, String contenido, int estado, String type) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.contenido = contenido;
        this.estado = estado;
        this.type = type;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
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

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
