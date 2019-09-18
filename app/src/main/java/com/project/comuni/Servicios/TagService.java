package com.project.comuni.Servicios;

import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Tag;

import java.util.ArrayList;

public class TagService {

    private Tag tagImnportante = new Tag(1,"Importante","#EC6227","#ffffff");
    private Tag tagAyuda = new Tag(2, "Ayuda","#63a9ea","#ffffff");
    private Tag tagReuniones = new Tag(3,"Reuniones","#c3ea6f","#111111");

    public Tag getTagImnportante() {
        return tagImnportante;
    }

    public Tag getTagAyuda() {
        return tagAyuda;
    }

    public Tag getTagReuniones() {
        return tagReuniones;
    }
}
