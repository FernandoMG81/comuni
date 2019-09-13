package com.project.comuni.Servicios;

import com.project.comuni.Models.Espacio;

import java.util.ArrayList;

public class EspacioService {

    private ArrayList<Espacio> espacios= new ArrayList<>();

    private Espacio matematica = new Espacio(
            1,"Matemática",
            "Este es un espacio para que realicen las consultas necesarias sobre la materia.",
            "01/06/2019");
    private Espacio programacion = new Espacio(
            2,"Programación",
            "En esta materia veremos diagramas de flujos. Es el primer paso antes de salir a programar.",
            "01/06/2019");
    private Espacio laboratorio = new Espacio(
            3,"Laboratorio de Programación I",
            "Vamos a embarcarnos de lleno en el mundo de la programacion mediante C, realizando aplicaciones de consola.",
            "01/06/2019");
    private Espacio procesamiento = new Espacio(
            4,"Sisteme de Procesamiento de datos",
            "Veremos como funciona físicamente y como calcula los datos la computadora. Quiero oir sus consultas!.",
            "01/06/2019");

    public ArrayList<Espacio> fillData(){
        espacios.add(matematica);
        espacios.add(programacion);
        espacios.add(laboratorio);
        espacios.add(procesamiento);
        return espacios;
    }

    public EspacioService() { }

    public Espacio getMatematica() {
        return matematica;
    }

    public Espacio getProgramacion() {
        return programacion;
    }

    public Espacio getLaboratorio() {
        return laboratorio;
    }

    public Espacio getProcesamiento() {
        return procesamiento;
    }
}
