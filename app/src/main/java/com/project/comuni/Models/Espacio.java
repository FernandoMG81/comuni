package com.project.comuni.Models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Map;

public class Espacio implements Serializable {

    private String nombre;
    private String descripcion;
    private Map<String,Usuario> administradores;
    private Map<String,Usuario> miembros;
    private String espacioUrl;
    private String creado;
    private String deleted;

    public Espacio() { }

    public Espacio(String nombre, String descripcion, Map<String, Usuario> administradores, Map<String, Usuario> miembros, String espacioUrl, String creado, String deleted) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.administradores = administradores;
        this.miembros = miembros;
        this.espacioUrl = espacioUrl;
        this.creado = creado;
        this.deleted = deleted;
    }

    public String validar(){
        if (nombre.isEmpty()){
            return "Un espacio debe tener nombre.";
        }
        if (descripcion.isEmpty()){
            return "Un espacio debe tener descripción.";
        }
        if (administradores.isEmpty()){
            return "Un debe tener al menos 1 administrador.";
        }
        return "Ok";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Map<String, Usuario> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(Map<String, Usuario> administradores) {
        this.administradores = administradores;
    }

    public Map<String, Usuario> getMiembros() {
        return miembros;
    }

    public void setMiembros(Map<String, Usuario> miembros) {
        this.miembros = miembros;
    }

    public String getEspacioUrl() {
        return espacioUrl;
    }

    public void setEspacioUrl(String espacioUrl) {
        this.espacioUrl = espacioUrl;
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

    @Override
    public String toString(){
        return this.nombre;
    }
}