package com.project.comuni.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comuni.Adapters.RecyclerAdapterNews;
import com.project.comuni.Models.Noticia;
import com.project.comuni.R;
import com.project.comuni.Servicios.NoticiaService;

import java.util.ArrayList;

public class InnerNoticiasFragment extends Fragment {

    private Noticia noticia = new Noticia();

    private TextView titulo;
    private TextView texto;
    private TextView usuario;
    private ImageView imagenUsuario;

    public void setNoticia() {
        Bundle bundle = getArguments();
        this.noticia = (Noticia) bundle.getSerializable("noticia");
    }

    public void cargarPagina (@NonNull View itemView){
        titulo = itemView.findViewById(R.id.InnerNewsTitulo);
        texto = itemView.findViewById(R.id.InnerNewsTexto);
        usuario = itemView.findViewById(R.id.InnerNewsUsuario);
        imagenUsuario = itemView.findViewById(R.id.InnerNewsFotoUsuario);

        titulo.setText(this.noticia.getTitulo());
        texto.setText(this.noticia.getTexto());
        usuario.setText(this.noticia.getUsuario().getNombre() +" " + this.noticia.getUsuario().getApellido());
        imagenUsuario.setImageResource(this.noticia.getImagen());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.setNoticia();
        View view = inflater.inflate(R.layout.fragment_inner_news, container, false);
        this.cargarPagina(view);
        return view;
    }

}
