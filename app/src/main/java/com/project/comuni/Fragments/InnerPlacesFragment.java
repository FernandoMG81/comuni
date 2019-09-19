package com.project.comuni.Fragments;


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

import com.project.comuni.Adapters.RecyclerAdapterComentarios;
import com.project.comuni.Models.Comentario;
import com.project.comuni.Models.Noticia;
import com.project.comuni.Models.Post;
import com.project.comuni.R;
import com.project.comuni.Servicios.ComentarioService;
import com.project.comuni.Servicios.PostService;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class InnerPlacesFragment extends Fragment {

    private TextView Titulo;
    private TextView Descripcion;
    private TextView Tag;
    private TextView NombreUsuario;
    private TextView Fecha;

    private Post post;
    private PostService postService = new PostService();

    private ArrayList<Comentario> comentarios = new ArrayList<>();
    private ComentarioService comentarioService= new ComentarioService();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_posts, container, false);

        post = postService.filterPostById(1);

        Titulo = view.findViewById(R.id.InnerPlacesTitulo);
        Descripcion = view.findViewById(R.id.InnerPlacesDescripcion);
        Tag = view.findViewById(R.id.InnerPlacesTag);
        NombreUsuario = view.findViewById(R.id.InnerPlacesUsuario);
        Fecha = view.findViewById(R.id.InnerPlacesFecha);

        Titulo.setText(post.getTitulo());
        Descripcion.setText(post.getTexto());
        Tag.setText(post.getTag().getText());
        Tag.setTextColor(Color.parseColor(post.getTag().getTextColor()));
        Tag.setBackgroundColor(Color.parseColor(post.getTag().getBackgroundColor()));
        NombreUsuario.setText(post.getUsuario().getNombre());
        Fecha.setText(post.getCreado());


        RecyclerView recyclerView = view.findViewById(R.id.RVInnerPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        comentarios = comentarioService.getComentarios();
        RecyclerAdapterComentarios adapter = new RecyclerAdapterComentarios(comentarios,this.getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
