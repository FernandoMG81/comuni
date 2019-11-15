package com.project.comuni.Models;


import com.project.comuni.Models.Firebase.Go;

public class Mensaje {

    private Go<Usuario> Emisor;
    private Go<Usuario> Receptor;
    private String urlFoto;
    private String texto;
    private Object created;

    public Mensaje() { }

    public Mensaje(Go<Usuario> emisor, Go<Usuario> receptor, String texto, String urlFoto, Object created) {
        Emisor = emisor;
        Receptor = receptor;
        this.urlFoto = urlFoto;
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

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
