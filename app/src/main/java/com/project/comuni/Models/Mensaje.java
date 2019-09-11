package com.project.comuni.Models;

public class Mensaje {
    private int id;
    private int postId;
    private int UsuarioId;
    private String mensaje;

    public Mensaje(int id, int postId, int usuarioId, String mensaje) {
        this.id = id;
        this.postId = postId;
        UsuarioId = usuarioId;
        this.mensaje = mensaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
