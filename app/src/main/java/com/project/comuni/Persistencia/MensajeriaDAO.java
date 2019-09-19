package com.project.comuni.Persistencia;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.project.comuni.Models.Firebase.MensajePersonal;
import com.project.comuni.Utils.Constantes;

import static com.project.comuni.Utils.Constantes.NODO_MENSAJES;

public class MensajeriaDAO {

    private static MensajeriaDAO mensajeriaDAO;
    private FirebaseDatabase database;
    private DatabaseReference referenceMensajeria;

    public static MensajeriaDAO getInstance(){
        if(mensajeriaDAO==null){
            mensajeriaDAO=new MensajeriaDAO();
        }
        return mensajeriaDAO;
    }

    private MensajeriaDAO(){
        database = FirebaseDatabase.getInstance();
        referenceMensajeria = database.getReference(NODO_MENSAJES);
       // storage = FirebaseStorage.getInstance();
       // referenceUsuarios = database.getReference(Constantes.NODO_USUARIOS);
       // referenceFotoPerfil = storage.getReference("Fotos/FotoPerfil/"+getKeyUsuario());
    }

    public void nuevoMensaje(String keyEmisor, String keyReceptor, MensajePersonal mensaje){
        DatabaseReference referenceEmisor = referenceMensajeria.child(keyEmisor).child(keyReceptor);
        DatabaseReference referenceReceptor = referenceMensajeria.child(keyReceptor).child(keyEmisor);
        referenceEmisor.push().setValue(mensaje);
        referenceReceptor.push().setValue(mensaje);
    }
}
