package com.project.comuni.Servicios;

import com.project.comuni.Models.Usuario;

public class UsuarioService {
    private Usuario profesor = new Usuario(
            1,
            "Damian",
            "Natale",
            "dnatale",
            "asdasd",
            "dnatale@comuni.com",
            "01/01/2019");

    private Usuario alumno1 = new Usuario(
            1,
            "Javier",
            "Angelelli",
            "jangelelli@comuni.com",
            "asdasd",
            "jangelelli@comuni.com",
            "01/01/2019");

    private Usuario alumno2 = new Usuario(
            1,
            "Fernando",
            "Gordillo",
            "fgordillo",
            "asdasd",
            "fgordillo@comuni.com",
            "01/01/2019");

    private Usuario alumno3 = new Usuario(
            1,
            "Alan",
            "Murua",
            "amurua",
            "asdasd",
            "amurua@comuni.com",
            "01/01/2019");

    private Usuario alumno4 = new Usuario(
            1,
            "Federico",
            "Vega",
            "fvega",
            "asdasd",
            "fvega@comuni.com",
            "01/01/2019");

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

