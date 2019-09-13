package com.project.comuni.Models;


public class Mensaje {

    private int id;
    private Usuario Emisor;
    private Usuario Receptor;
    private String texto;
    private String created;

    public Mensaje(int id, Usuario emisor, Usuario receptor, String texto, String created) {
        this.id = id;
        Emisor = emisor;
        Receptor = receptor;
        this.texto = texto;
        this.created = created;
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
