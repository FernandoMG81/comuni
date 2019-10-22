package com.project.comuni.Servicios;

import com.project.comuni.Models.Mensaje;
import com.project.comuni.Models.Usuario;

import java.util.ArrayList;

public class MensajeService {
    private UsuarioService usuarioService = new UsuarioService();
    private ArrayList<Mensaje> mensajes = new ArrayList<>();

    public MensajeService() {
        fillData();
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }

    public  ArrayList<Mensaje> filterMensajesPorNVueltas (int numero){
        ArrayList<Mensaje> mensajesFiltrados = new ArrayList<>();
        for (int vuelta = 0; vuelta<numero; vuelta++){
         mensajesFiltrados.add(mensajes.get(vuelta));
        }
        return mensajesFiltrados;
    }
//    public ArrayList<Mensaje> filterUnMensajePorContacto (Usuario usuarioLogueado){
//
//        ArrayList<Mensaje> mensajesFiltrados = new ArrayList<>();
//        Boolean Agregar;
//
//
//        for (Mensaje mensaje:mensajes) {
//            Agregar = false;
//            if ( (mensaje.getEmisor().getId() == usuarioLogueado.getId()) || (mensaje.getReceptor().getId() == usuarioLogueado.getId()) ){
//               if (mensajesFiltrados.size()== 0){ Agregar = true; }
//
//                for (Mensaje mensajeFiltrado:mensajesFiltrados) {
//                    if (mensaje.getReceptor().getId() == usuarioLogueado.getId() & mensajeFiltrado.getReceptor().getId() == usuarioLogueado.getId()) {
//                        if(mensaje.getEmisor().getId() == mensajeFiltrado.getEmisor().getId()) {
//                            Agregar = true;
//                        }
//                    }
//                    else if (mensaje.getEmisor().getId() == usuarioLogueado.getId() & mensajeFiltrado.getEmisor().getId() == usuarioLogueado.getId()) {
//                        if(mensaje.getReceptor().getId() == mensajeFiltrado.getReceptor().getId()) {
//                            Agregar = true;
//                        }
//                    }
//                    else if (mensaje.getReceptor().getId() == usuarioLogueado.getId() & mensajeFiltrado.getEmisor().getId() == usuarioLogueado.getId()) {
//                        if(mensaje.getEmisor().getId() == mensajeFiltrado.getReceptor().getId()) {
//                            Agregar = true;
//                        }
//                    }
//                    else if (mensaje.getEmisor().getId() == usuarioLogueado.getId() & mensajeFiltrado.getReceptor().getId() == usuarioLogueado.getId()) {
//                        if(mensaje.getReceptor().getId() == mensajeFiltrado.getEmisor().getId()) {
//                            Agregar = true;
//                        }
//                    }
//
//                }
//                if (Agregar == true) {
//                    mensajesFiltrados.add(mensaje);
//                }
//            }
//        }
//        return mensajesFiltrados;
//    }

    public ArrayList<Mensaje> filterByEmisorNombre(String Nombre,String Apellido){

        ArrayList<Mensaje> mensajesFiltrados = new ArrayList<>();

        for (Mensaje mensaje:mensajes) {
            if (mensaje.getEmisor().getNombre() == Nombre & mensaje.getEmisor().getApellido() == Apellido){
              mensajesFiltrados.add(mensaje);
            }
        }
        return mensajesFiltrados;
    }

    private ArrayList<Mensaje> fillData() {

        mensajes.add(new Mensaje("1",
                usuarioService.getProfesor(),
                usuarioService.getAlumno4(),
                "Recorda traer el justificativo mañana.",
                "15/09;"
        ));

        mensajes.add(new Mensaje("2",
                usuarioService.getAlumno5(),
                usuarioService.getProfesor(),
                "Che, de casualidad,tendrás los apuntes de progrmación al día?",
                "10/09;"
        ));

        mensajes.add(new Mensaje("3",
                usuarioService.getAlumno3(),
                usuarioService.getProfesor(),
                "Mañana sale juntarnos a estudiar? Me esta volviendo loco esto del switch.",
                "15/09;"
        ));

        mensajes.add(new Mensaje("4",
                usuarioService.getAlumno4(),
                usuarioService.getProfesor(),
                "Hiciste el TP 2? Necesito una mano si podés.",
                "15/09;"
        ));

        mensajes.add(new Mensaje("5",
                usuarioService.getAlumno1(),
                usuarioService.getProfesor(),
                "No encuentro mis apuntes...",
                "10/09;"
        ));

    return mensajes;
    }

}
