package com.project.comuni.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.comuni.Models.Noticia;
import com.project.comuni.R;

public class InnerPlacesFragment extends Fragment {

    private Noticia noticia = new Noticia();

    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_posts, container, false);

        return view;
    }

}
