package com.project.comuni.Models;



public class Usuario {

    private String id = "-1";
    private String nombre = null;
    private String apellido = null;
    private int foto;
    private String creado = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    private String email = null;
    private String contrasena = null;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String apellido, int foto, String creado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.creado = creado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }
}
