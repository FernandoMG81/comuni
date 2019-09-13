package com.project.comuni.Models;


public class Noticia {

    private int id;
    private Usuario usuario;
    private String titulo;
    private String texto;
    private String created;

    public Noticia() { }

    public Noticia(int id, Usuario usuario, String titulo, String texto, String created) {
        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.texto = texto;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}

