package com.project.comuni.Models;

import java.util.Date;

public class Espacio {
    private int id = -1;
    private String nombre = null;
    private String descripcion = null;

    // Para despues
    private Date creado = null;
    private Date modificado = null;
    private Date eliminado = null;

    public Espacio(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public Date getEliminado() {
        return eliminado;
    }

    public void setEliminado(Date eliminado) {
        this.eliminado = eliminado;
    }
}
