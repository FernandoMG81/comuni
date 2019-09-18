package com.project.comuni.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;


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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment implements RecyclerAdapterNews.OnItemListener {

    private ArrayList<Noticia> noticias = new ArrayList<>();
    private SearchView search;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RVNews);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NoticiaService noticiaService= new NoticiaService();

        this.noticias = noticiaService.getNoticias();

        RecyclerAdapterNews adapter = new RecyclerAdapterNews(this.noticias, this.getContext(),this);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: Clicked.");
    }
}
