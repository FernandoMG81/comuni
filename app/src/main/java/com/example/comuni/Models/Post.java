package com.example.comuni.Models;

import java.util.Date;

public class Post {
    private int id = -1;
    private int espacioId = -1;
    private int usuarioId = -1;
    private String titulo;
    private String mensaje;
    private int upvotes;
    private boolean importante;

    // Para despues
    private Date creado = null;
    private Date modificado = null;
    private Date eliminado = null;

    public Post(int id, int espacioId, int usuarioId, String titulo, String mensaje, int upvotes, boolean importante) {
        this.id = id;
        this.espacioId = espacioId;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.upvotes = upvotes;
        this.importante = importante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(int espacioId) {
        this.espacioId = espacioId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public boolean isImportante() {
        return importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public Date getEliminado() {
        return eliminado;
    }

    public void setEliminado(Date eliminado) {
        this.eliminado = eliminado;
    }
}
