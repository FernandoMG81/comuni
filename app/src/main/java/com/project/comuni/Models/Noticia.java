package com.project.comuni.Models;


import android.net.Uri;

import com.google.firebase.database.ServerValue;
import com.project.comuni.Models.Firebase.Go;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Noticia implements Serializable {


    private Go<Usuario> usuario;
    private String titulo;
    private String texto;
    private Object timeStamp;
    private String created;
    private String newsKey;
    private String nombre;
    private String fotoUrl;
    private String userKey;

    public Noticia() { }

    public Noticia(Go<Usuario> usuario, String titulo, String texto){
        this.usuario = usuario;
        this.titulo = titulo;
        this.texto = texto;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public Noticia(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
        //this.created = created;
        //this.imagen = imagen;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    //Constructor provisorio
    public Noticia(String nombre, String fotoUrl, String titulo, String texto, String userKey){
        this.nombre = nombre;
        this.fotoUrl = fotoUrl;
        this.titulo = titulo;
        this.texto = texto;
        this.userKey = userKey;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
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

    public String getNewsKey() {
        return newsKey;
    }

    public void setNewsKey(String newsKey) {
        this.newsKey = newsKey;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
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

