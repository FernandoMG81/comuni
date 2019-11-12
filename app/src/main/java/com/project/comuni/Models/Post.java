package com.project.comuni.Models;

import com.project.comuni.Models.Firebase.Go;

import java.io.Serializable;
import java.util.ArrayList;



public class Post implements Serializable {

    private Go<Espacio> espacio = new Go<>();
    private Go<Usuario> usuario = new Go<>();
    private String titulo;
    private String texto;
    private int upvotes;
    private Go<Tag> tag = new Go<>();
    private String created = "";

    public Post(String id, Go<Espacio>  espacio, Go<Usuario> usuario, String titulo, String texto, int upvotes, Go<Tag> tag, String created) {

        this.espacio = espacio;
        this.usuario = usuario;
        this.titulo = titulo;
        this.texto = texto;
        this.upvotes = upvotes;
        this.tag = tag;
        this.created = created;
    }

    public Post() {    }

    public String validar(){
        if (titulo.isEmpty()){
            return "El post debe tener título.";
        }
        if (texto.isEmpty()){
            return "El post debe tener un cuerpo.";
        }
        if (tag == null){
            return "Ok";
        }
        if (espacio == null){
            return "Ocurrió un error.";
        }
        if (usuario == null){
            return "Ocurrió un error.";
        }
        return "Ok";
    }

    public Go<Espacio>  getEspacio() {
        return espacio;
    }

    public void setEspacio(Go<Espacio>  espacio) {
        this.espacio = espacio;
    }

    public Go<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(Go<Usuario> usuario) {
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

    public Go<Tag> getTag() {
        return tag;
    }

    public void setTags(Go<Tag> tag) {
        this.tag = tag;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String creado) {
        this.created = creado;
    }

}
