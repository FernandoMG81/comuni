package com.project.comuni.Models;

import java.util.ArrayList;
import java.util.Date;

public class Post {

    private int id = -1;
    private Espacio espacio;
    private Usuario usuario;
    private String titulo;
    private String texto;
    private int upvotes;
    private ArrayList <Tag> tags;
    private Date creado = null;

    public Post(int id, Espacio espacio, Usuario usuario, String titulo, String texto, int upvotes, ArrayList<Tag> tags, Date creado) {
        this.id = id;
        this.espacio = espacio;
        this.usuario = usuario;
        this.titulo = titulo;
        this.texto = texto;
        this.upvotes = upvotes;
        this.tags = tags;
        this.creado = creado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }
}
