package com.project.comuni.Models;


import java.util.Dictionary;
import java.util.Map;

public class Usuario {

    private String email;
    private String nombre;
    private String apellido;
    private Map<String,Espacio> administradores;
    private Map<String,Espacio>  miembros;
    private String foto;
    private String creado;

    private String deleted;

    public Usuario() { }

    public Usuario(String nombre, String apellido, String foto, String creado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.creado = creado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Map<String, Espacio> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(Map<String, Espacio> administradores) {
        this.administradores = administradores;
    }

    public Map<String, Espacio> getMiembros() {
        return miembros;
    }

    public void setMiembros(Map<String, Espacio> miembros) {
        this.miembros = miembros;
    }
}
