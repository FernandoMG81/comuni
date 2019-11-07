package com.project.comuni.Models;


import com.project.comuni.Models.Firebase.Go;

public class Mensaje {

    private Go<Usuario> Emisor;
    private Go<Usuario> Receptor;
    private String texto;
    private String created;

    public Mensaje() { }

    public Mensaje(Go<Usuario> emisor, Go<Usuario> receptor, String texto, String created) {
        Emisor = emisor;
        Receptor = receptor;
        this.texto = texto;
        this.created = created;
    }

    public Go<Usuario> getEmisor() {
        return Emisor;
    }

    public void setEmisor(Go<Usuario> emisor) {
        Emisor = emisor;
    }

    public Go<Usuario> getReceptor() {
        return Receptor;
    }

    public void setReceptor(Go<Usuario> receptor) {
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
