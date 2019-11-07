package com.project.comuni.Models;


import com.google.firebase.database.ServerValue;
import com.project.comuni.Models.Firebase.Go;

import java.io.Serializable;

public class Noticia implements Serializable {


    private Go<Usuario> usuario;
    private String titulo;
    private String texto;
    private String created;
    private Object timeStamp;

    public Noticia() { }

    public Noticia(Go<Usuario> usuario, String titulo, String texto, String created ){
        this.usuario = usuario;
        this.titulo = titulo;
        this.texto = texto;
        this.created = created;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public Noticia(String titulo, String texto) {
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

    public Go<Usuario> getUserId() {
        return usuario;
    }

    public void setUserId(Go<Usuario> usuario) {
        this.usuario = usuario;
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

