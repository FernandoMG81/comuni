package com.example.comuni.Models;

import java.util.Date;

public class Usuario {
    private int id = -1;
    private String nombre = null;
    private String apellido = null;
    private String Usuario = null;
    private String Contrasenia = null;
    private String mail = null;
    //Para despues
    private Date creado = null;
    private Date modificado = null;
    private Date eliminado = null;

    public Usuario(int id , String nombre, String apellido, String usuario, String contrasenia, String mail) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        Usuario = usuario;
        Contrasenia = contrasenia;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getUsuario() {
        return Usuario;
    }

    public String getContrasenia() {
        return Contrasenia;
    }

    public String getMail() {
        return mail;
    }

    public Date getCreado() {
        return creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public Date getEliminado() {
        return eliminado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public void setContrasenia(String contrasenia) {
        Contrasenia = contrasenia;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public void setEliminado(Date eliminado) {
        this.eliminado = eliminado;
    }
}
