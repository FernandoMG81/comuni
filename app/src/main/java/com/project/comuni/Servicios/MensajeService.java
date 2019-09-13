package com.project.comuni.Servicios;

import com.project.comuni.Models.Mensaje;

import java.util.ArrayList;

public class MensajeService {
    private UsuarioService usuarioService = new UsuarioService();
    private ArrayList<Mensaje> mensajes = new ArrayList<>();

    public ArrayList<Mensaje> fillData() {

        mensajes.add(new Mensaje(1,
                usuarioService.getProfesor(),
                null,
                "Recorda traer el justificativo mañana.",
                "15/09;"
        ));

        mensajes.add(new Mensaje(1,
                usuarioService.getAlumno1(),
                null,
                "Che, de casualidad,tendrás los apuntes de progrmación al día?",
                "10/09;"
        ));

        mensajes.add(new Mensaje(1,
                usuarioService.getAlumno2(),
                null,
                "Mañana sale juntarnos a estudiar? Me esta volviendo loco esto del switch.",
                "15/09;"
        ));

        mensajes.add(new Mensaje(1,
                usuarioService.getAlumno3(),
                null,
                "Hiciste el TP 2? Necesito una mano si podés.",
                "15/09;"
        ));

    return mensajes;
    }

}
