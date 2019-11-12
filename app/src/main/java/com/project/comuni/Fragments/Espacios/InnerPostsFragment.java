package com.project.comuni.Fragments.Espacios;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterComentarios;
import com.project.comuni.Models.Comentario;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Post;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.ComentarioService;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.PostService;
import com.project.comuni.Servicios.UsuarioService;

import java.util.ArrayList;

public class InnerPostsFragment extends Fragment {

    //Variables
    private Go<Espacio> espacio = new Go<>();
    private Go<Usuario> usuario = new Go<>();
    private Go<Post> post = new Go<>();
    private ArrayList<Go<Comentario>> comentarios = new ArrayList<>();

    //Layout
    private TextView Titulo;
    private TextView Descripcion;
    private TextView Tag;
    private TextView NombreUsuario;
    private TextView Fecha;
    private RecyclerView recyclerView;

    public void getData() {
        Bundle bundle = getArguments();
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
        this.post = (Go<Post>) bundle.getSerializable("post");
        post.getObject().setEspacio(espacio);
    }

    public void setLayout(View view){
        Titulo = view.findViewById(R.id.InnerPlacesTitulo);
        Descripcion = view.findViewById(R.id.InnerPlacesDescripcion);
        Tag = view.findViewById(R.id.InnerPlacesTag);
        NombreUsuario = view.findViewById(R.id.InnerPlacesUsuario);
        Fecha = view.findViewById(R.id.InnerPlacesFecha);
        recyclerView = view.findViewById(R.id.RVInnerPosts);

        Titulo.setText(post.getObject().getTitulo());
        Descripcion.setText(post.getObject().getTexto());
        Tag.setText(post.getObject().getTag().getObject().getText());
        Tag.setTextColor(Color.parseColor(post.getObject().getTag().getObject().ColorT()));
        Tag.setBackgroundColor(Color.parseColor(post.getObject().getTag().getObject().ColorB()));
        NombreUsuario.setText(post.getObject().getUsuario().getObject().getNombre()
                + " " + post.getObject().getUsuario().getObject().getApellido());
        Fecha.setText(post.getObject().getCreated());
    }

    public void setRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterComentarios adapter = new RecyclerAdapterComentarios(comentarios,this.getContext());
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_posts, container, false);

        getData();
        setLayout(view);

            new ComentarioService().getAllFromPost(post)
                    .addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot x : snapshot.getChildren()) {
                                Go<Comentario> comentariox = new Go<>(new Comentario());
                                comentariox.setKey(x.getKey());
                                comentariox.setObject(x.getValue(comentariox.getObject().getClass()));
                                comentarios.add(comentariox);
                            }

                                setRecycler();

                        }
                    });

        return view;
    }

}
