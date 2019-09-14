package com.project.comuni.Servicios;

import com.project.comuni.Models.Post;

import java.util.ArrayList;

public class PostService {

    private ArrayList<Post> posts = new ArrayList<>();
    private UsuarioService usuarioService = new UsuarioService();
    private EspacioService espacioService = new EspacioService();

    public PostService() {
        fillData();
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<Post> filterByEspacioNombre(String nombre){
       ArrayList<Post> postsFiltrados = new ArrayList<>();
        for (Post post:posts) {
            if(post.getEspacio().getNombre()==nombre){
                postsFiltrados.add(post);
            }
        }
        return postsFiltrados;
    }

    private ArrayList<Post> fillData(){
    //Posteos Matematica
    Post post = new Post(
            1,
            espacioService.filterByName("Matemática"),
            usuarioService.getProfesor(),
            "Recuerden que el 20/09 tenemos parcial.",
            "No van a poder usar calculadora, deberán traer solo lápiz, goma y papel.",
            7,
            null,
            "10/09/2019"
            );
    posts.add(post);
     post = new Post(
            2,
            espacioService.filterByName("Matemática"),
            usuarioService.getAlumno3(),
            "¿Alguien resolvió el ejercicio 2.A?","Me da raiz cuadrada de -2...",
            4,
            null,
            "7/09/2019");
     posts.add(post);
    post = new Post(
            3,
            espacioService.filterByName("Matemática"),
            usuarioService.getAlumno4(),
            "¿Se puede sumar dos matrices de distinto orden?",
            "Hice la suma, pero no se si esta bien",
            1,
            null,
            "23/08/2019");
    posts.add(post);
     post = new Post(
            4,
            espacioService.filterByName("Matemática"),
            usuarioService.getAlumno1(),
            "Alguien resolvió elejercicio 1.E de la guía?",
            "Simplemente no me sale.",
            1,
            null,
            "20/08/2019");
     posts.add(post);

    //Posteos Programación
    post = new Post(
            5,
            espacioService.filterByName("Programación"),
            usuarioService.getProfesor(),
            "Hoy no voy a poder asistir a clases.",
            "Les pido perdón por avisar a tan corto plazo, pero ocurrió un imprevisto.",
            0,
            null,
            "20/08/2019");
    posts.add(post);
    post = new Post (
            6,
            espacioService.filterByName("Programación"),
            usuarioService.getAlumno1(),
            "Problemas con el fopen",
            "¿Qué parametros toma?",
            3,
            null,
            "20/08/2019");
    posts.add(post);
    post = new Post (
            7,
            espacioService.filterByName("Programación"),
            usuarioService.getAlumno4(),
            "¿Cómo era el tema del encapsulamiento?",
            "No entiendo como accedo a un atributo private si, justamente, lo hace inaccesible...",
            4,
            null,
            "20/08/2019");
    posts.add(post);

    //Posteos Laboratorio
    post = new Post(
            8,
            espacioService.filterByName("Laboratorio de Programación"),
            usuarioService.getAlumno1(),
            "Problemas instalando SQLServer.",
            "¿Alguno me podrá pasar un tutorial?",
            3,
            null,
            "20/08/2019");
    posts.add(post);

        return posts;
    }




}
