package com.project.comuni.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.comuni.Adapters.RecyclerAdapterMessages;
import com.project.comuni.Adapters.RecyclerAdapterPlaces;
import com.project.comuni.Models.Post;
import com.project.comuni.R;
import com.project.comuni.Servicios.PostService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlacesFragment extends Fragment {

    private ArrayList<Post> posts = new ArrayList<>();
    private PostService postService = new PostService();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_places, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.RVPlaces);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        posts = postService.fillDataMatematica();

        RecyclerAdapterPlaces adapter = new RecyclerAdapterPlaces(posts, this.getContext());

        recyclerView.setAdapter(adapter);

        return view;
    }
}
