package com.project.comuni.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.RecyclerAdapterNews;
import com.project.comuni.Models.Noticia;
import com.project.comuni.R;
import com.project.comuni.Servicios.NoticiaService;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsFragment extends Fragment implements RecyclerAdapterNews.OnItemListener {

    private ArrayList<Noticia> noticias = new ArrayList<>();
    private Button addNewsButton;
    private RecyclerView recyclerNews;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerNews = view.findViewById(R.id.RVNews);
        addNewsButton = view.findViewById(R.id.newsButton);

        recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));
        NoticiaService noticiaService= new NoticiaService();

        this.noticias = noticiaService.getNoticias();

        RecyclerAdapterNews adapter = new RecyclerAdapterNews(this.noticias, this.getContext(),this);

        recyclerNews.setAdapter(adapter);

        addNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new addNewsFragment();
                Bundle args = new Bundle();
                //args.putSerializable("NoticiaActual", noticiaActual);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: Clicked.");
    }
}
