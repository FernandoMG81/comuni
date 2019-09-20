package com.project.comuni.Servicios;

import android.graphics.drawable.Drawable;

import com.project.comuni.Models.Usuario;
import com.project.comuni.R;

public class UsuarioService {
    private Usuario profesor = new Usuario(
            1,
            "Damian",
            "Natale",
            R.drawable.damian_natale,
            "asdasd");

    private Usuario alumno1 = new Usuario(
            2,
            "Javier",
            "Angelelli",
            R.drawable.javier_angelelli,
            "asdasd");

    private Usuario alumno2 = new Usuario(
            3,
            "Fernando",
            "Gordillo",
            R.drawable.damian_natale,
            "asdasd");

    private Usuario alumno3 = new Usuario(
            4,
            "Alan",
            "Murua",
            R.drawable.javier_angelelli,
            "asdasd");

    private Usuario alumno4 = new Usuario(
            5,
            "Federico",
            "Vega",
            R.drawable.federico_vega,
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

