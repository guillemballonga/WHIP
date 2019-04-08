package com.bernal.jonatan.whip.Models;

import java.util.Date;

public class Post {


    private String nombre;
    private int imagen;
    private String contenido;
    private boolean estado;
    private String id_post;
    private String tipo;
    private String raza;
    private Date createdAt;

    public Post(String nombre, int imagen, String contenido, boolean estado, String id_post, String tipo, String raza, Date createdAt) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.contenido = contenido;
        this.estado = estado;
        this.id_post = id_post;
        this.tipo = tipo;
        this.raza = raza;
        this.createdAt = createdAt;
    }

    public Post() {
    }

    public Post(String nombre, String content, int imagen, String id){
        this.contenido = content;
        this.nombre = nombre;
        this.imagen = imagen;
        this.id_post = id;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }







}
