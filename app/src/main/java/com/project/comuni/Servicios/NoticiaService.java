package com.project.comuni.Servicios;

import android.media.Image;
import android.widget.ImageView;

import com.project.comuni.Models.Noticia;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.recyclerview.widget.DividerItemDecoration;

public class NoticiaService {

    private UsuarioService usuarioService = new UsuarioService();
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

        noticia = new Noticia();
        noticia.setId(1);
        noticia.setUsuario(usuarioService.getProfesor());
        noticia.setTitulo("Recordatorio");
        noticia.setTexto("Si el 20 clasificamos a la final están todos aprobados los que cursan conmigo, a darle caña a ese código!!!");
        noticia.setCreated("18/09/2019");
        noticia.setImagen(R.mipmap.user_default);
        noticias.add(noticia);


        noticia = new Noticia();
        noticia.setId(1);
        noticia.setUsuario(usuarioService.getProfesor());
        noticia.setTitulo("Palabras de aliento");
        noticia.setTexto("Vamos chicos, estoy muy orgulloso de ustedes y de las ganas que estan poniendole a este proyecto. Vamos a México");
        noticia.setCreated("17/09/2019");
        noticia.setImagen(R.mipmap.user_default);
        noticias.add(noticia);

        noticias.add( new Noticia(
                0,
                usuarioService.getProfesor(),
                "La universidad rankea en el puesto 21",
                "Según la organizacion SQL nuestra universidad se encuentra en el 21° puesto " +
                        "gracias a la excelente formación de sus profesores y a su espiritu orientado al emprendedurismo",
                "15/09/2019",R.mipmap.user_default));


        noticia = new Noticia();
        noticia.setId(1);
        noticia.setUsuario(usuarioService.getProfesor());
        noticia.setTitulo("Felicitaciones al equipo de Ecoauto");
        noticia.setTexto("Cinco alumnos de ingeniería mecánica alcanzaron el 2do puesto en la competencia Ecoauto, " +
                "por una movilidad sustentable.");
        noticia.setCreated("23/08/2019");
        noticia.setImagen(R.mipmap.user_default);
        noticias.add(noticia);

        noticia = new Noticia();
        noticia.setUsuario(usuarioService.getProfesor());
        noticia.setId(2);
        noticia.setTitulo("La universidad sigue creciendo");
        noticia.setTexto("La universidad ha sido la que más a crecido porcentualmente dentro de Argentina. Entre las razones que nos elijen se encuentran:" +
                "la excelente formación academica, el prestigio del egresado, la orientación hacia soluciones pŕácticas.");
        noticia.setCreated("21/06/2019");
        noticia.setImagen(R.mipmap.user_default);
        noticias.add(noticia);

        noticia = new Noticia();
        noticia.setUsuario(usuarioService.getProfesor());
        noticia.setId(3);
        noticia.setTitulo("Abrió la competencia TuAPP");
        noticia.setTexto("Ha comenzado la competencia interuniversitaria e internacional donde los alumnos deben crear una aplicación" +
                "pasando por todas las etapas necesarias para ello, desde la formaciión de la idea, hasta el código mismo.");
        noticia.setCreated("13/04/2019");
        noticia.setImagen(R.mipmap.user_default);
        noticias.add(noticia);

        return noticias;
    }



}
