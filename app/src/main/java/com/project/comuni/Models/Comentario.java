package com.project.comuni.Models;

import com.project.comuni.Models.Firebase.Go;

public class Comentario {


    private Go<Post> post;
    private Go<Usuario> usuario;
    private String texto;
    private String created;

    public Comentario(Go<Post> post, Go<Usuario> usuario, String texto, String created) {
        this.post = post;
        this.usuario = usuario;
        this.texto = texto;
        this.created = created;
    }

    public Comentario() {
    }


    public Go<Post> getPost() {
        return post;
    }

    public void setPost(Go<Post> post) {
        this.post = post;
    }

    public Go<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(Go<Usuario> usuario) {
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
