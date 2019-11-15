package com.project.comuni.Fragments.Espacios;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InnerPostsFragment extends Fragment {

    //Variables
    private Go<Comentario> comentario = new Go<>(new Comentario());
    private Go<Espacio> espacio = new Go<>(new Espacio());
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private Go<Post> post = new Go<>(new Post());
    private ArrayList<Go<Comentario>> comentarios = new ArrayList<>();

    //Layout
    //Post
    private TextView Titulo;
    private TextView Descripcion;
    private TextView Tag;
    private TextView NombreUsuario;
    private TextView Fecha;
    private TextView TextoVacio;
    //Recycler Comentarios
    private RecyclerView recyclerView;
    //Escribir Comentario
    TextView comentarioTexto;
    Button comentarioSubmit;

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

        TextoVacio = view.findViewById(R.id.InnerPostsVacio);
        TextoVacio.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.RVInnerPosts);

        comentarioTexto = view.findViewById(R.id.InnerFragmentComentarioTexto);
        comentarioSubmit = view.findViewById(R.id.InnerFragmentComentarioSubmit);

        Titulo.setText(post.getObject().getTitulo());
        Descripcion.setText(post.getObject().getTexto());
        if (post.getObject().getTag().getObject() != null) {
            Tag.setText(post.getObject().getTag().getObject().getText());
            Tag.setTextColor(Color.parseColor(post.getObject().getTag().getObject().ColorT()));
            Tag.setBackgroundColor(Color.parseColor(post.getObject().getTag().getObject().ColorB()));
        }
        NombreUsuario.setText(post.getObject().getUsuario().getObject().getNombre()
                + " " + post.getObject().getUsuario().getObject().getApellido());
        Fecha.setText(post.getObject().getCreated());
    }

    public void setRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterComentarios adapter = new RecyclerAdapterComentarios(comentarios,this.getContext());
        recyclerView.setAdapter(adapter);
    }

    public void setBoton(){
        comentarioSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comentario.getObject().setTexto(comentarioTexto.getText().toString());
                comentario.getObject().setPost(post);
                comentario.getObject().setUsuario(usuario);
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
                comentario.getObject().setCreated(sdf.format(date.getTime()));

                new ComentarioService(comentario).create()
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_posts, container, false);

        getData();
        setLayout(view);
        setBoton();
            new ComentarioService().getAllFromPost(post)
                    .addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            comentarios.clear();
                            for (DataSnapshot x : snapshot.getChildren()) {
                                Go<Comentario> comentariox = new Go<>(new Comentario());
                                comentariox.setKey(x.getKey());
                                comentariox.setObject(x.getValue(comentariox.getObject().getClass()));
                                comentarios.add(comentariox);
                            }
                                if(comentarios.size()>0) {
                                    setRecycler();
                                    TextoVacio.setVisibility(View.GONE);
                                }
                                else{
                                    TextoVacio.setVisibility(View.VISIBLE);
                                }
                        }
                    });

        return view;
    }

}
