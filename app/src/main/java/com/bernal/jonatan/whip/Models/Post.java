package com.bernal.jonatan.whip.Models;

public class Post {


    private String nombre;
    private String imagen;
    private String contenido;
    private String id;
    private String type;


    public Post(String id, String nombre, String imagen, String contenido, String type) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.contenido = contenido;
        this.type = type;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public String getContenido() {
        return contenido;
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
