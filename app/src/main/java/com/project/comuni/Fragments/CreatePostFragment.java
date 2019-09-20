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

import com.project.comuni.Adapters.RecyclerAdapterTags;
import com.project.comuni.Models.Noticia;
import com.project.comuni.Models.Tag;
import com.project.comuni.R;
import com.project.comuni.Servicios.NoticiaService;
import com.project.comuni.Servicios.TagService;

import java.util.ArrayList;

public class CreatePostFragment extends Fragment {

    private TagService tagService = new TagService();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RVTags);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerAdapterTags adapter = new RecyclerAdapterTags(this.tagService.filterTagsByEspacioId(1), this.getContext());

        recyclerView.setAdapter(adapter);

        return view;
    }

}
