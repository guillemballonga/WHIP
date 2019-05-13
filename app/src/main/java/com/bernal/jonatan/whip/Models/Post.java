package com.bernal.jonatan.whip.Models;

import java.util.Date;

public class Post {


    private String nombre;
    private String imagen;
    private String contenido;
    private String id;
    private String type;
    private String title;
    private String specie;
    private String race;
    private String userId;
    private String[] createdAt;
    private Boolean status;


    public Post(String id, String nombre, String imagen, String contenido, String type) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.contenido = contenido;
        this.type = type;
    }


    public Post(String title, String[] createdAt, String specie, String race, String contenido, String userId, String imagen, Boolean status) {
        this.imagen = imagen;
        this.contenido = contenido;
        this.title = title;
        this.specie = specie;
        this.race = race;
        this.userId = userId;
        this.createdAt = createdAt;
        this.status = status;
    }


    public String getTitle() {
        return title;
    }


    public String getSpecie() {
        return specie;
    }


    public String getRace() {
        return race;
    }


    public String getUserId() {
        return userId;
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

    public String[] getCreatedAt() {
        return createdAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setRace(String race) {
        this.race = race;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(String[] createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
