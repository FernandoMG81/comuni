package com.project.comuni.Persistencia;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Firebase.User;
import com.project.comuni.Models.Logica.LUser;
import com.project.comuni.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;

public class UsuarioDAO {


    private static UsuarioDAO usuarioDAO;
    private FirebaseDatabase database;
    private DatabaseReference referenceUsuarios;

    public static UsuarioDAO getInstance(){
        if(usuarioDAO==null){
            usuarioDAO=new UsuarioDAO();

        }
        return usuarioDAO;
    }

    private UsuarioDAO(){
        database = FirebaseDatabase.getInstance();
        referenceUsuarios = database.getReference(Constantes.NODO_USUARIOS);
    }

    public String getKeyUsuario(){
        return FirebaseAuth.getInstance().getUid();
    }

    public long fechaDeCreacionLong(){
        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getCreationTimestamp();
    }

    public long fechaDeUltimaConexionLong(){
        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getLastSignInTimestamp();
    }

    //Solo para actualizar una vez los usuarios que no tienen foto de perfil
    public void añadirFotosDePerfilUsuariosSinFotos(){
        referenceUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<LUser> lUsuariosLista = new ArrayList<>();
                for (DataSnapshot childDataSnapShot : dataSnapshot.getChildren()){
                    User usuario = childDataSnapShot.getValue(User.class);
                    LUser lUsuario = new LUser(childDataSnapShot.getKey(),usuario);
                    lUsuariosLista.add(lUsuario);
                }

                for(LUser lUsuario : lUsuariosLista){
                    if(lUsuario.getUsuario().getFotoPerfilURL()==null){
                        referenceUsuarios.child(lUsuario.getKey()).child("fotoPerfilURL").setValue(Constantes.URL_FOTO_POR_DEFECTO_USUARIOS);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
