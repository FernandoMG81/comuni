package com.project.comuni.Models;

public class Mensaje {
    private int id;
    private Usuario Emisor;
    private Usuario Receptor;
    private String mensaje;

    public Mensaje(int id, Usuario emisor, Usuario receptor, String mensaje) {
        this.id = id;
        Emisor = emisor;
        Receptor = receptor;
        this.mensaje = mensaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getEmisor() {
        return Emisor;
    }

    public void setEmisor(Usuario emisor) {
        Emisor = emisor;
    }

    public Usuario getReceptor() {
        return Receptor;
    }

    public void setReceptor(Usuario receptor) {
        Receptor = receptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
