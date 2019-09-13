package com.project.comuni.Models;

public class Espacio {

    private int id = -1;
    private String nombre = null;
    private String texto = null;
    private String creado = null;

    public Espacio(int id, String nombre, String texto, String creado) {
        this.id = id;
        this.nombre = nombre;
        this.texto = texto;
        this.creado = creado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }
}