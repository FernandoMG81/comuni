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

import com.project.comuni.Adapters.Espacios.RecyclerAdapterComentarios;
import com.project.comuni.Models.Comentario;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Post;
import com.project.comuni.R;
import com.project.comuni.Servicios.ComentarioService;

import java.util.ArrayList;

public class InnerPostsFragment extends Fragment {

    private TextView Titulo;
    private TextView Descripcion;
    private TextView Tag;
    private TextView NombreUsuario;
    private TextView Fecha;

    private Go<Post> post = new Go<>();

    private ArrayList<Go<Comentario>> comentarios = new ArrayList<>();
    private ComentarioService comentarioService = new ComentarioService();

    public void setPost() {
        Bundle bundle = getArguments();
        this.post = (Go<Post>) bundle.getSerializable("post");
        Go<Comentario> comentario = new Go<>();
        comentario.getObject().setPost(post);
        comentarios = new ComentarioService(comentario).getAll();
    }

    public void setLayout(View view){
        Titulo = view.findViewById(R.id.InnerPlacesTitulo);
        Descripcion = view.findViewById(R.id.InnerPlacesDescripcion);
        Tag = view.findViewById(R.id.InnerPlacesTag);
        NombreUsuario = view.findViewById(R.id.InnerPlacesUsuario);
        Fecha = view.findViewById(R.id.InnerPlacesFecha);

        Titulo.setText(post.getObject().getTitulo());
        Descripcion.setText(post.getObject().getTexto());
        Tag.setText(post.getObject().getTag().getObject().getText());
        Tag.setTextColor(Color.parseColor(post.getObject().getTag().getObject().getTextColor()));
        Tag.setBackgroundColor(Color.parseColor(post.getObject().getTag().getObject().getBackgroundColor()));
        NombreUsuario.setText(post.getObject().getUsuario().getObject().getNombre()
                + " " + post.getObject().getUsuario().getObject().getApellido());
        Fecha.setText(post.getObject().getCreated());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setPost();
        View view = inflater.inflate(R.layout.fragment_inner_posts, container, false);
        setLayout(view);

        RecyclerView recyclerView = view.findViewById(R.id.RVInnerPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterComentarios adapter = new RecyclerAdapterComentarios(comentarios,this.getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
