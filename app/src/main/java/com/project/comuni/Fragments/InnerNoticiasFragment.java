package com.project.comuni.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_news, container, false);

        return view;
    }

}
