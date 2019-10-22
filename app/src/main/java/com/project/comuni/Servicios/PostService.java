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

    public ArrayList<Post> filterByEspacioId (String id){
        ArrayList<Post> postsFiltrados = new ArrayList<>();
        for (Post post:posts) {
            if(post.getEspacio().getId()==id){
                postsFiltrados.add(post);
            }
        }
        return postsFiltrados;
    }

    private ArrayList<Post> fillData(){
    //Posteos Matematica
    Post post = new Post(
            "1",
            espacioService.filterEspacioById("1"),
            usuarioService.getProfesor(),
            "Recuerden que el 20/09 tenemos parcial.",
            "No van a poder usar calculadora, deberán traer solo lápiz, goma y papel.",
            7,
            tagService.filterTagById("1"),
            "10/09/2019"
            );
    posts.add(post);
     post = new Post(
            "2",
            espacioService.filterEspacioById("1"),
            usuarioService.getAlumno3(),
            "¿Alguien resolvió el ejercicio 2.A?","Me da raiz cuadrada de -2. Eso es imposible no? Creo que la raiz cuadrada de -2 es un imposible.",
            4,
             tagService.filterTagById("2"),
            "7/09/2019");

     posts.add(post);
        post = new Post(
                "3",
                espacioService.filterEspacioById("1"),
                usuarioService.getAlumno1(),
                "Alguien quiere juntarse a estudiar?",
                "Vengo flojo para el parcial y me gustaría estudiar con alguien, asi podemos avanzar más rápido!",
                1,
                tagService.filterTagById("3"),
                "06/09/2019");
        posts.add(post);

    post = new Post(
            "4",
            espacioService.filterEspacioById("1"),
            usuarioService.getAlumno4(),
            "¿Se puede sumar dos matrices de distinto orden?",
            "Hice la suma, pero no se si esta bien. Me da que es de 4*4, y para lo que vimos en clase, creo que se me hizo muy grande.",
            1,
            tagService.filterTagById("2"),
            "03/09/2019");
    posts.add(post);


    //Posteos Programación
    post = new Post(
            "5",
            espacioService.filterEspacioById("2"),
            usuarioService.getProfesor(),
            "Hoy no voy a poder asistir a clases.",
            "Les pido perdón por avisar a tan corto plazo, pero ocurrió un imprevisto.",
            0,
            tagService.filterTagById("1"),
            "20/08/2019");
    posts.add(post);
    post = new Post (
            "6",
            espacioService.filterEspacioById("2"),
            usuarioService.getAlumno1(),
            "Problemas con el fopen",
            "¿Qué parametros toma?",
            3,
            tagService.filterTagById("2"),
            "20/08/2019");
    posts.add(post);
    post = new Post (
            "7",
            espacioService.filterEspacioById("2"),
            usuarioService.getAlumno4(),
            "¿Cómo era el tema del encapsulamiento?",
            "No entiendo como accedo a un atributo private si, justamente, lo hace inaccesible...",
            4,
            tagService.filterTagById("2"),
            "20/08/2019");
    posts.add(post);

    //Posteos Laboratorio
    post = new Post(
            "8",
            espacioService.filterEspacioById("3"),
            usuarioService.getAlumno1(),
            "Problemas instalando SQLServer.",
            "¿Alguno me podrá pasar un tutorial?",
            3,
            tagService.filterTagById("2"),
            "20/08/2019");
    posts.add(post);

        //Posteos Sistema de...
        post = new Post(
                "9",
                espacioService.filterEspacioById("4"),
                usuarioService.getAlumno1(),
                "Ayuda con numeros Binarios",
                "La verdad me pierdo un poco con el tema. Cuando los numeros " +
                        "binarios no son de 4 cifras todo ok, pero cuando hablamos de BCD me pierdo.",
                3,
                tagService.filterTagById("4"),
                "20/09/2019");
        posts.add(post);

        post = new Post(
                "10",
                espacioService.filterEspacioById("4"),
                usuarioService.getAlumno2(),
                "Alguien para juntarse el sábado a estudiar?",
                "Los primeros temas me va re bien. Si saben de lectura de disco, eso es lo que mas me falta.",
                3,
                tagService.filterTagById("5"),
                "20/09/2019");
        posts.add(post);

        post = new Post(
                "10",
                espacioService.filterEspacioById("4"),
                usuarioService.getAlumno4(),
                "El sistema Aiken",
                "Me re pertido con este tema, y por lo poco que entiendo, es algo bastante importante",
                3,
                tagService.filterTagById("4"),
                "20/09/2019");
        posts.add(post);


        return posts;
    }

    public  Post  filterPostById(String id){
        for (Post post:posts) {
            if (post.getId() == id){
                return post;
            }
        }
        Post post = new Post();
        return post;
    }

}
