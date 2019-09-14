package com.project.comuni.Servicios;

import android.media.Image;
import android.widget.ImageView;

import com.project.comuni.Models.Noticia;

import java.util.ArrayList;
import java.util.Calendar;

public class NoticiaService {

    private ArrayList<Noticia> noticias = new ArrayList<>();
    private Noticia noticia = new Noticia();

    public NoticiaService() {
        fillData();
    }

    public ArrayList<Noticia> getNoticias() {
        return noticias;
    }

//    public ArrayList<Noticia> FilterBy(){
//
//    }

    public ArrayList<Noticia> fillData(){

        noticias.add( new Noticia(0, null,"La universidad rankea en el puesto 21",
                "Según la organizacion SQL nuestra universidad se encuentra en el 21° puesto " +
                        "gracias a la excelente formación de sus profesores y a su espiritu orientado al emprendedurismo",
                "15/09/2019"));


        noticia.setId(1);
        noticia.setTitulo("Felicitaciones al equipo de Ecoauto");
        noticia.setTexto("Cinco alumnos de ingeniería mecánica alcanzaron el 2do puesto en la competencia Ecoauto, " +
                "por una movilidad sustentable.");
        noticia.setCreated("23/08/2019");
        noticias.add(noticia);

        noticia = new Noticia();
        noticia.setId(2);
        noticia.setTitulo("La universidad sigue creciendo");
        noticia.setTexto("La universidad ha sido la que más a crecido porcentualmente dentro de Argentina. Entre las razones que nos elijen se encuentran:" +
                "la excelente formación academica, el prestigio del egresado, la orientación hacia soluciones pŕácticas.");
        noticia.setCreated("21/06/2019");
        noticias.add(noticia);

        noticia = new Noticia();
        noticia.setId(3);
        noticia.setTitulo("Abrió la competencia TuAPP");
        noticia.setTexto("Ha comenzado la competencia interuniversitaria e internacional donde los alumnos deben crear una aplicación" +
                "pasando por todas las etapas necesarias para ello, desde la formaciión de la idea, hasta el código mismo.");
        noticia.setCreated("13/04/2019");
        noticias.add(noticia);


        return noticias;
    }



}
