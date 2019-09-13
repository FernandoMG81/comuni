package com.project.comuni.Servicios;

import com.project.comuni.Models.Post;

import java.util.ArrayList;

public class PostService {

    private ArrayList<Post> posts = new ArrayList<>();
    private UsuarioService usuarioService = new UsuarioService();
    private EspacioService espacioService = new EspacioService();

    //Posteos Matematica
    private Post post1 = new Post(
            1,
            espacioService.getMatematica(),
            usuarioService.getProfesor(),
            "Recuerden que el 20/09 tenemos parcial.",
            "No van a poder usar calculadora, deberán traer solo lápiz, goma y papel.",
            7,
            null,
            "10/09/2019"
            );
    private Post post2 = new Post(
            2,
            espacioService.getMatematica(),
            usuarioService.getAlumno3(),
            "¿Alguien resolvió el ejercicio 2.A?","Me da raiz cuadrada de -2...",
            4,
            null,
            "7/09/2019");
    private Post post3 = new Post(
            3,
            espacioService.getMatematica(),
            usuarioService.getAlumno4(),
            "¿Se puede sumar dos matrices de distinto orden?",
            "Hice la suma, pero no se si esta bien",
            1,
            null,
            "23/08/2019");
    private Post post4 = new Post(
            4,
            espacioService.getMatematica(),
            usuarioService.getAlumno1(),
            "Alguien resolvió elejercicio 1.E de la guía?",
            "Simplemente no me sale.",
            1,
            null,
            "20/08/2019");

    //Posteos Programación
    private Post post5 = new Post(
            5,
            espacioService.getProgramacion(),
            usuarioService.getProfesor(),
            "Hoy no voy a poder asistir a clases.",
            "Les pido perdón por avisar a tan corto plazo, pero ocurrió un imprevisto.",
            0,
            null,
            "20/08/2019");
    private Post post6 = new Post (
            6,
            espacioService.getProgramacion(),
            usuarioService.getAlumno1(),
            "Problemas con el fopen",
            "¿Qué parametros toma?",
            3,
            null,
            "20/08/2019");
    private Post post7 = new Post (
            7,
            espacioService.getProgramacion(),
            usuarioService.getAlumno4(),
            "¿Cómo era el tema del encapsulamiento?",
            "No entiendo como accedo a un atributo private si, justamente, lo hace inaccesible...",
            4,
            null,
            "20/08/2019");

    //Posteos Laboratorio
    private Post post8 = new Post(
            8,
            espacioService.getLaboratorio(),
            usuarioService.getAlumno1(),
            "Problemas instalando SQLServer.",
            "¿Alguno me podrá pasar un tutorial?",
            3,
            null,
            "20/08/2019");

    public ArrayList<Post> fillDataMatematica(){
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);

        return posts;
    }

    public PostService() { }

    public Post getPost1() {
        return post1;
    }

    public Post getPost2() {
        return post2;
    }

    public Post getPost3() {
        return post3;
    }

    public Post getPost4() {
        return post4;
    }

    public Post getPost5() {
        return post5;
    }

    public Post getPost6() {
        return post6;
    }

    public Post getPost7() {
        return post7;
    }

    public Post getPost8() {
        return post8;
    }
}
