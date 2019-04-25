package com.bernal.jonatan.whip;

public class Fuente {


    private String nombre;
    private String imagen;
    private String contenido;
    private int estado;
    String id;
    String type;
    private String fecha;


    Fuente(String id, String nombre, String imagen, String contenido, int estado, String type) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.contenido = contenido;
        this.estado = estado;
        this.type = type;
    }

    Fuente(String id, String user, String imagen, String contenido, String fecha) {
        this.id = id;
        this.nombre = user;
        this.imagen = imagen;
        this.contenido = contenido;
        this.fecha = fecha;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    String getContenido() {
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
