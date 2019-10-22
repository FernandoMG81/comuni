package com.project.comuni.Models;

import java.io.Serializable;
import java.util.ArrayList;



public class Post implements Serializable {

    private String id = "-1";
    private Espacio espacio;
    private Usuario usuario;
    private String titulo;
    private String texto;
    private int upvotes;
    private Tag tag;
    private String creado = null;

    public Post(String id, Espacio espacio, Usuario usuario, String titulo, String texto, int upvotes, Tag tag, String creado) {
        this.id = id;
        this.espacio = espacio;
        this.usuario = usuario;
        this.titulo = titulo;
        this.texto = texto;
        this.upvotes = upvotes;
        this.tag = tag;
        this.creado = creado;
    }

    public Post() {    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
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

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTags(Tag tag) {
        this.tag = tag;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

}
