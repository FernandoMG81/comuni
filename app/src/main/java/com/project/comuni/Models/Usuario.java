package com.project.comuni.Models;


import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseUser;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Usuario {

    private String email;
    private String nombre;
    private String apellido = "";
    private Map<String,Espacio> administradores = new HashMap<>();
    private Map<String,Espacio>  miembros = new HashMap<>();
    private String foto;
    private String creado;
    private String token;

    private String deleted;

    public Usuario() { }

    public Usuario(FirebaseUser usuariox) {
        email = usuariox.getEmail();
        nombre = usuariox.getDisplayName();
    }

    public Usuario(Map.Entry<String,Usuario> usuario) {
        this.nombre = usuario.getValue().getNombre();
        this.apellido = usuario.getValue().getApellido();
        this.administradores = usuario.getValue().getAdministradores();
        this.miembros = usuario.getValue().getMiembros();
        this.foto = usuario.getValue().getFoto();
        this.creado = usuario.getValue().getCreado();
        this.deleted = usuario.getValue().getDeleted();
    }

    public Usuario(String nombre, String apellido, String foto, String creado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.creado = creado;
    }

    public Boolean validarRegistro(String contrasena1, String contrasena2){
        if (nombre.isEmpty()){ return false; }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        if (!contrasena1.equals(contrasena2)){ return  false; }
        if( contrasena1.length() < 6){ return false; }
        return true;
    }

    public Boolean administrador(String key){
        for (Map.Entry<String, Espacio> espacio: administradores.entrySet())
        {
            if (espacio.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
