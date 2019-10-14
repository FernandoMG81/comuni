package com.project.comuni.Servicios;

import com.project.comuni.Models.Comentario;

import java.util.ArrayList;

public class ComentarioService {
    private UsuarioService usuarioService = new UsuarioService();
    private PostService postService = new PostService();
    private ArrayList<Comentario> comentarios = new ArrayList<>();

    public void fillData() {
        comentarios.add(new Comentario(
                1,
                postService.filterPostById(1),
                usuarioService.getAlumno1(),
                "Matrices entra profe?",
                "10/09/2019"
        ));

        comentarios.add(new Comentario(
                2,
                postService.filterPostById(1),
                usuarioService.getAlumno3(),
                "Yo entendi que matrices si lo toma.",
                "10/09/2019"
        ));
        comentarios.add(new Comentario(
                3,
                postService.filterPostById(1),
                usuarioService.getProfesor(),
                "Si chicos, entra todo lo que vimos hasta la última clase.",
                "10/09/2019"
        ));
    }

    public ComentarioService() {
        fillData();
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public ArrayList<Comentario> getComentariosByEspacioId(int id) {

        ArrayList<Comentario> comentariosAux = new ArrayList<>();
        for (Comentario comentario:this.getComentarios()) {
            if (comentario.getPost().getId() == id){
                comentariosAux.add(comentario);
            }
        }
        return comentariosAux;
    }
}
