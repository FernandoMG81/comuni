package com.project.comuni.Models;

public class Comentario {

    private String id;
    private Post post;
    private Usuario usuario;
    private String texto;
    private String created;

    public Comentario(String id, Post post, Usuario usuario, String texto, String created) {
        this.id = id;
        this.post = post;
        this.usuario = usuario;
        this.texto = texto;
        this.created = created;
    }

    public Comentario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
