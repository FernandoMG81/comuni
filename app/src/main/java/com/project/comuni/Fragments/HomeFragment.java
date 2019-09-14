package com.project.comuni.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.RecyclerAdapterNews;
import com.project.comuni.Models.Noticia;
import com.project.comuni.R;
import com.project.comuni.Servicios.NoticiaService;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    private ArrayList<Noticia> noticias = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RVNews);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NoticiaService noticiaService= new NoticiaService();

        this.noticias = noticiaService.getNoticias();

        RecyclerAdapterNews adapter = new RecyclerAdapterNews(this.noticias, this.getContext());

        recyclerView.setAdapter(adapter);

        return view;
    }

}
