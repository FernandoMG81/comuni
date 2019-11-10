package com.project.comuni.Fragments.Noticias;


import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Noticia;
import com.project.comuni.R;

public class InnerNoticiasFragment extends Fragment {

    private Go<Noticia> noticia = new Go<>();

    private TextView titulo;
    private TextView texto;
    private TextView usuario;
    private ImageView imagenUsuario;
    private TextView fecha;

    public void setNoticia() {
        Bundle bundle = getArguments();
        this.noticia = (Go<Noticia>) bundle.getSerializable("noticia");
    }

    public void cargarPagina (@NonNull View itemView){
        titulo = itemView.findViewById(R.id.InnerNewsTitulo);
        texto = itemView.findViewById(R.id.InnerNewsTexto);
        usuario = itemView.findViewById(R.id.InnerNewsUsuario);
        imagenUsuario = itemView.findViewById(R.id.InnerNewsFotoUsuario);

        titulo.setText(this.noticia.getObject().getTitulo());
        texto.setText(this.noticia.getObject().getTexto());
        Linkify.addLinks(texto, Linkify.WEB_URLS);
        usuario.setText(this.noticia.getObject().getNombre());
        Glide.with(getContext()).load(this.noticia.getObject().getFotoUrl()).into(imagenUsuario);
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
