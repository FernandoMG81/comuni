package com.project.comuni.Servicios;

import com.project.comuni.Models.Post;

import java.util.ArrayList;

public class PostService {

    private ArrayList<Post> posts = new ArrayList<>();
    private UsuarioService usuarioService = new UsuarioService();
    private EspacioService espacioService = new EspacioService();
    private TagService tagService = new TagService();

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
            tagService.getTagImnportante(),
            "10/09/2019"
            );
    posts.add(post);
     post = new Post(
            2,
            espacioService.filterByName("Matemática"),
            usuarioService.getAlumno3(),
            "¿Alguien resolvió el ejercicio 2.A?","Me da raiz cuadrada de -2. Eso es imposible no? Creo que la raiz cuadrada de -2 es un imposible.",
            4,
             tagService.getTagAyuda(),
            "7/09/2019");

     posts.add(post);
        post = new Post(
                3,
                espacioService.filterByName("Matemática"),
                usuarioService.getAlumno1(),
                "Alguien quiere juntarse a estudiar?",
                "Vengo flojo para el parcial y me gustaría estudiar con alguien, asi podemos avanzar más rápido!",
                1,
                tagService.getTagReuniones(),
                "06/09/2019");
        posts.add(post);

    post = new Post(
            4,
            espacioService.filterByName("Matemática"),
            usuarioService.getAlumno4(),
            "¿Se puede sumar dos matrices de distinto orden?",
            "Hice la suma, pero no se si esta bien. Me da que es de 4*4, y para lo que vimos en clase, creo que se me hizo muy grande.",
            1,
            tagService.getTagAyuda(),
            "03/09/2019");
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
