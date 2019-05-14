package com.bernal.jonatan.whip.Models;

public class Comment {

    private String user;
    private String imagen;
    private String contenido;
    private String id;
    private String fecha;


    public Comment(String id, String user, String imagen, String contenido, String fecha) {
        this.id = id;
        this.user = user;
        this.imagen = imagen;
        this.contenido = contenido;
        this.fecha = fecha;
    }


    public String getUser() {
        return user;
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

    public String getFecha() {
        return fecha;
    }
}
