package com.project.comuni.Models;


import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseUser;
import com.project.comuni.Models.Firebase.Go;

import java.util.ArrayList;
import java.util.Collections;
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

    public Usuario(String nombre, String apellido, String foto, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.email = email;
    }

    public Usuario returnSmall(){
        return new Usuario(
                nombre,
                apellido,
                foto,
                email
        );
    }

    public ArrayList<Go<Espacio>> returnAllEspacios(){
        ArrayList<Go<Espacio>> espacios = new ArrayList<>();
        Go<Espacio> espaciox;
        for (Map.Entry<String,Espacio> x : administradores.entrySet())
        {
            espaciox = new Go<>(x.getKey(),x.getValue());
            espacios.add(espaciox);
        }
        for (Map.Entry<String,Espacio> x : miembros.entrySet())
        {
            espaciox = new Go<>(x.getKey(),x.getValue());
            espacios.add(espaciox);
        }
        return espacios;
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

    public String validarDatos(){
        if (nombre.isEmpty()){
            return "Debe completar su nombre.";
        }
        if (apellido.isEmpty()){
            return "Debe completar su apellido";
        }
        return "Ok";
    }

    public String validarEmail(){
        if (TextUtils.isEmpty(email)) {
            return "No completo el mail";
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "No ingresó un mail válido";
        }
        return "Ok";
    }

    public String validarContrasenas(String contrasena, String contrasena2){
        if (!(contrasena.length() >= 6) ){
            return "La contraseña debe tener al menos 6 caracteres";
        }
        if (!contrasena.equals(contrasena2)){
            return "Las contraseñas no coinciden";
        }
        return "Ok";
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
