package com.project.comuni.Servicios;

import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Tag;

import java.util.ArrayList;

public class TagService {

    private ArrayList<Tag> tags = new ArrayList<>();
    private EspacioService espacioService = new EspacioService();



    public void fillData(){
        tags.add(new Tag(
                1,
                new Espacio(),
                "Importante",
                "#EC6227",
                "#ffffff"));
        tags.add(new Tag(
                2,
                espacioService.filterEspacioById("1"),
                "Ayuda",
                "#63a9ea",
                "#ffffff"));
        tags.add(new Tag(
                3,
                espacioService.filterEspacioById("1"),
                "Reuniones",
                "#c3ea6f",
                "#111111"));
        tags.add(new Tag(
                4,
                espacioService.filterEspacioById("4"),
                "N° Binarios",
                "#8B4513",
                "#ffffff"));
        tags.add(new Tag(
                5,
                espacioService.filterEspacioById("4"),
                "Disco",
                "#FBFB82",
                "#111111"));

    }

    public ArrayList<Tag> filterTagsByEspacioId(String id) {
        ArrayList<Tag> filterTags = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getEspacio().getId() == id || tag.getEspacio().getId() == "-1") {
                filterTags.add(tag);
            }
        }
        return filterTags;
    }

    public Tag filterTagById(int id) {
        for (Tag tag : tags) {
            if (tag.getId() == id) {
                return tag;
            }
        }
        Tag tag = new Tag();
        return tag;
    }

    public TagService() {
        fillData();
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

}
