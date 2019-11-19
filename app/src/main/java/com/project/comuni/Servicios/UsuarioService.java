package com.project.comuni.Servicios;

import android.graphics.drawable.Drawable;
import android.renderscript.Sampler;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Models.Comentario;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Noticia;
import com.project.comuni.Models.Post;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Utils.Constantes;
import com.project.comuni.Utils.FireUrl;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UsuarioService {

    private Db db = new Db();

    private FireUrl url = new FireUrl(Constantes.NODO_USUARIOS);
    private ArrayList<String> urlEspacios;
    private ArrayList<String> urlEspaciosAdministradores;
    private ArrayList<String> urlEspaciosMiembros;

    private Go<Usuario> usuario = new Go<>();

    private ArrayList<String> setUrlEspacios(Map<String,Espacio> espacios){
        ArrayList<String> Arrayx = new ArrayList<>();
        for (Map.Entry<String,Espacio> x:espacios.entrySet()) {
            url.getRootInEspacios(new Go<Espacio>(x));
        }
        return Arrayx;
    }

    public UsuarioService(){

    }

    public UsuarioService(Go<Usuario> usuariox){
        usuario = usuariox;
    }

    public Task create (){
        return db.create(usuario,url.getRoot());
    }

    public Task updateSoloUsuario() {
        return db.update(usuario,url.getRoot());
    }

    public Task update () {
        return db.update(usuario, url.getRoot())
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
                    @Override
                    public void onComplete(@NonNull Task<Transaction.Result> task) {
                        if (task.isSuccessful()) {
                            if(urlEspacios!= null){
                                for (String x : urlEspacios) {
                                    db.update(usuario, x);
                                }
                            }
                        }
                    }
                });
    }

    public Task delete (){
         return db.delete(usuario, url.getRoot())
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
                    @Override
                    public void onComplete(@NonNull Task<Transaction.Result> task) {
                        if (task.isSuccessful()) {
                            for (String x : urlEspacios) {
                                db.delete(usuario, x);
                            }
                        }
                    }
                });
    }

    public Query getObject(){
       return db.getObject(usuario.getKey(),url.getRoot());
    }

    public Query getAll(){
        return db.DbRef().child(url.getRoot());
    }

    public Boolean isAdmin(Go<Espacio> espacio){
        for (Map.Entry<String,Espacio> x: usuario.getObject().getAdministradores().entrySet())
        {
                if(x.getKey().equals(espacio.getKey())){
                    return true;
                }
        }
        return false;
    }

    public Task updateUsuarioEverywhere(){
        return db.update(usuario, url.getRoot())
                .addOnCompleteListener(new OnCompleteListener<Transaction.Result>() {
                    @Override
                    public void onComplete(@NonNull Task<Transaction.Result> task) {
                        if (task.isSuccessful()) {
                            if (urlEspacios != null) {
                                for (String x : urlEspacios) {
                                    db.update(usuario, x);
                                }

                                db.getAll(Constantes.NODO_NOTICIAS).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                                            Go<Noticia> noticia = new Go<>(new Go<>(new Noticia()));
                                            if (x.getValue(noticia.getObject().getClass()).getUserKey().equals(usuario.getKey())) {
                                                Map<String, Object> y = new HashMap<>();
                                                y.put("userKey", usuario.getKey());
                                                y.put("nombre", usuario.getObject().getNombre() + " " + usuario.getObject().getApellido());
                                                db.DbRef().child(Constantes.NODO_NOTICIAS).child(x.getKey()).updateChildren(y);
                                            }
                                        }
                                    }
                                });

                                if (urlEspacios != null) {
                                    for (String x : urlEspacios) {
                                        String urlEspacioActual = x;
                                        db.getAll(url.AddKey(x, url.getPosts()))
                                                .addListenerForSingleValueEvent(
                                                        new ValueEventListener() {
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }

                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshotx) {
                                                                for (DataSnapshot y : dataSnapshotx.getChildren()) {
                                                                    Go<Post> post = new Go<>(new Post());
                                                                    post.setKey(y.getKey());
                                                                    if (y.getValue(post.getObject().getClass()).getUsuario().getKey().equals(usuario.getKey())) {
                                                                        db.DbRef().child(urlEspacioActual)
                                                                                .child(url.getPosts())
                                                                                .child("usuario")
                                                                                .setValue(usuario);
                                                                    }
                                                                    new ComentarioService().getAllFromPost(post)
                                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshotz) {
                                                                                    for (DataSnapshot z : dataSnapshotz.getChildren()) {
                                                                                        Comentario comentario = new Comentario();
                                                                                        if (z.getValue(comentario.getClass()).getUsuario().getKey().equals(usuario.getKey())) {
                                                                                            db.DbRef().child(urlEspacioActual)
                                                                                                    .child(url.getPosts())
                                                                                                    .child("usuario")
                                                                                                    .child("Comentarios")
                                                                                                    .setValue(usuario);
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                }
                                                                            });
                                                                    {

                                                                    }

                                                                }
                                                            }


                                                        }

                                                );
                                    }
                                }
                            }

                        }

                    }});}}