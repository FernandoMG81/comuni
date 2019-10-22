package com.project.comuni.Servicios;

import com.project.comuni.Models.Espacio;

import java.util.ArrayList;

public class EspacioService {

    private ArrayList<Espacio> espacios= new ArrayList<>();

    public ArrayList<Espacio> fillData(){

        Espacio espacio = new Espacio(
                "1","Matemática",
                "Este es un espacio para que realicen las consultas necesarias sobre la materia.",
                "01/06/2019");
        espacios.add(espacio);

        espacio = new Espacio(
                "2","Programación",
                "En esta materia veremos diagramas de flujos. Es el primer paso antes de salir a programar.",
                "01/06/2019");
        espacios.add(espacio);

        espacio = new Espacio(
                "3","Laboratorio de Programación I",
                "Vamos a embarcarnos de lleno en el mundo de la programacion mediante C, realizando aplicaciones de consola.",
                "01/06/2019");
        espacios.add(espacio);

        espacio = new Espacio(
                "4","Sistema de Procesamiento de datos",
                "Veremos como funciona físicamente y como calcula los datos la computadora. Quiero oir sus consultas!.",
                "01/06/2019");
        espacios.add(espacio);

        return espacios;
    }

    public EspacioService() {
        fillData();
    }

    public Espacio filterByName(String nombre){
        for (Espacio espacio:espacios) {
            if (espacio.getNombre() == nombre){
                return espacio;
            }
        }
        Espacio espacio = new Espacio();
        return espacio;
    }

    public Espacio filterEspacioById(String id){
        for (Espacio espacio:espacios) {
            if (espacio.getId() == id){
                return espacio;
            }
        }
        Espacio espacio = new Espacio();
        return espacio;
    }

    public ArrayList<Espacio> getEspacios() {
        return espacios;
    }
}
