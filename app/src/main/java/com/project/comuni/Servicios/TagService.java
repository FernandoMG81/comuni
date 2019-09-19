package com.project.comuni.Servicios;

import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Tag;

import java.util.ArrayList;

public class TagService {

    private ArrayList<Tag> tags = new ArrayList<>();

    private Tag tagImnportante = new Tag(1,"Importante","#EC6227","#ffffff");
    private Tag tagAyuda = new Tag(2, "Ayuda","#63a9ea","#ffffff");
    private Tag tagReuniones = new Tag(3,"Reuniones","#c3ea6f","#111111");

    public void fillData(){
        tags.add(tagImnportante);
        tags.add(tagAyuda);
        tags.add(tagReuniones);
    }

    public TagService() {
        fillData();
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

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
