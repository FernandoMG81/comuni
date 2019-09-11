package com.project.comuni.Servicios;

import com.project.comuni.Servicios.UsuariosService;
import com.project.comuni.Models.Mensaje;

import java.util.ArrayList;

public class MensajeService {
    private UsuariosService usuariosService;
    public ArrayList<Mensaje> mensajes = new ArrayList<>();

    public void fillData(){
        Mensaje xmensaje = new Mensaje(1,this.usuariosService.profesor,this.usuariosService.alumno1,"Acordate que mañana tenes que traerme el justificativo" );
        this.mensajes.add(xmensaje);
    }

}
