package com.project.comuni.Servicios;

import com.project.comuni.Models.Usuario;

public class UsuarioService {
    private Usuario profesor = new Usuario(
            1,
            "Damian",
            "Natale",
            "damian_natale.png",
            "asdasd");

    private Usuario alumno1 = new Usuario(
            2,
            "Javier",
            "Angelelli",
            "http://empleosm.com/resume/javier-txn5fldtv-psicologo-programador-tigre/",
            "asdasd");

    private Usuario alumno2 = new Usuario(
            3,
            "Fernando",
            "Gordillo",
            "javier_angelelli.png",
            "asdasd");

    private Usuario alumno3 = new Usuario(
            4,
            "Alan",
            "Murua",
            "javier_angelelli.png",
            "asdasd");

    private Usuario alumno4 = new Usuario(
            5,
            "Federico",
            "Vega",
            "@drawable/federico_vega.jpeg",
            "asdasd");

    public UsuarioService() { }

    public Usuario getProfesor() {
        return profesor;
    }

    public Usuario getAlumno1() {
        return alumno1;
    }

    public Usuario getAlumno2() {
        return alumno2;
    }

    public Usuario getAlumno3() {
        return alumno3;
    }

    public Usuario getAlumno4() {
        return alumno4;
    }
}

