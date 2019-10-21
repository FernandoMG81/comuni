package com.project.comuni.Fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton addNewsButton;
    private RecyclerView recyclerNews;
    private Dialog popAddNews;
    private ImageView popupAddBtn;
    private TextView popupTitle, popupDescription;
    ProgressBar popupClickProgress;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerNews = view.findViewById(R.id.RVNews);
        addNewsButton = view.findViewById(R.id.newsButton);

        recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));
        NoticiaService noticiaService= new NoticiaService();

        //Dialog nueva noticia
        iniPopup();

        this.noticias = noticiaService.getNoticias();

        RecyclerAdapterNews adapter = new RecyclerAdapterNews(this.noticias, this.getContext(),this);

        recyclerNews.setAdapter(adapter);

        addNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAddNews.show();
            }
        });

        return view;


    }

    private void iniPopup() {
        popAddNews = new Dialog(getContext());
        popAddNews.setContentView(R.layout.fragment_add_news);
        popAddNews.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddNews.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddNews.getWindow().getAttributes().gravity = Gravity.TOP;

        //Inicio de los widgets

        popupTitle = popAddNews.findViewById(R.id.createNewsTitulo);
        popupDescription = popAddNews.findViewById(R.id.createNewsDescripción);
        popupClickProgress = popAddNews.findViewById(R.id.createProgressBarNews);
        popupAddBtn = popAddNews.findViewById(R.id.createPostNews);


        //Agregar Noticia Listener

        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAddBtn.setVisibility(View.INVISIBLE);
                popupClickProgress.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: Clicked.");
    }
}
