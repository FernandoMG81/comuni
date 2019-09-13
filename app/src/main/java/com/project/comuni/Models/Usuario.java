package com.project.comuni.Models;


public class Usuario {

    private int id = -1;
    private String nombre = null;
    private String apellido = null;
    private String Usuario = null;
    private String Contrasenia = null;
    private String mail = null;
    private String creado = null;

    public Usuario(int id, String nombre, String apellido, String usuario, String contrasenia, String mail, String creado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        Usuario = usuario;
        Contrasenia = contrasenia;
        this.mail = mail;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getContrasenia() {
        return Contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        Contrasenia = contrasenia;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }
}
