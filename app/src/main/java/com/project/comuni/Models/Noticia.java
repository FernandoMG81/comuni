package com.project.comuni.Models;


import com.google.firebase.database.ServerValue;

import java.io.Serializable;

public class Noticia implements Serializable {

    private String newsKey;
    private String userId;
    private String titulo;
    private String texto;
    private String created;
    private Object timeStamp;

    public Noticia() { }

    public Noticia(String userId, String titulo, String texto) {
        this.userId = userId;
        this.titulo = titulo;
        this.texto = texto;
        //this.created = created;
        //this.imagen = imagen;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNewsKey() {
        return newsKey;
    }

    public void setNewsKey(String newsKey) {
        this.newsKey = newsKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }


}

