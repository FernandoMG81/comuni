package com.project.comuni.Models;

import androidx.annotation.Nullable;

import com.project.comuni.Models.Firebase.Go;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Espacio implements Serializable {

    private String nombre;
    private String descripcion;
    private Map<String,Usuario> administradores = new HashMap<>();
    private Map<String,Usuario> miembros = new HashMap<>();
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

    public Espacio(String nombre, String descripcion,String espacioUrl) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.espacioUrl = espacioUrl;
    }

    public Espacio returnSmall(){
        return new Espacio(
                nombre,
                descripcion,
                espacioUrl
        );
    }

    public Espacio returnSmallerMaps(){
        return new Espacio(
                nombre,
                descripcion,
                setSmallerUsuarios(administradores),
                setSmallerUsuarios(miembros),
                espacioUrl,
                creado,
                deleted
        );
    }

    private Map<String,Usuario> setSmallerUsuarios(Map<String,Usuario> usuarios){
        Map<String,Usuario> Arrayx = new HashMap<>();
        if (usuarios != null) {
            for (Map.Entry<String, Usuario> x : usuarios.entrySet()) {

                if (x.getValue().getAdministradores()!= null){
                    Map<String, Espacio> Adminsx = new HashMap<>();
                    for (Map.Entry<String, Espacio> y: x.getValue().getAdministradores().entrySet()) {
                        Adminsx.put(y.getKey(),y.getValue().returnSmall());

                    }
                    x.getValue().setAdministradores(Adminsx);
                }
                if (x.getValue().getMiembros()!= null){
                    Map<String, Espacio> miembrosx = new HashMap<>();
                    for (Map.Entry<String, Espacio> y: x.getValue().getMiembros().entrySet()) {
                        miembrosx.put(y.getKey(),y.getValue().returnSmall());
                    }
                    x.getValue().setAdministradores(miembrosx);
                }
                Arrayx.put(x.getKey(),x.getValue());
            }
        }
        return Arrayx;
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
        return (Map <String, Usuario>)administradores;
    }

    public void setAdministradores(Map<String, Usuario> administradores) {
        this.administradores = administradores;
    }

    public Map<String, Usuario> getMiembros() {
        return (Map <String, Usuario>) miembros;
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