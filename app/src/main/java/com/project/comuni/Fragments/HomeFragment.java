package com.project.comuni.Fragments;



import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;


import com.project.comuni.Adapters.RecycleAdapterNews;
import com.project.comuni.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {


    private List<String> news;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        news = this.getAllNews();

        mRecyclerView = view.findViewById(R.id.RVNews);
        mLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new RecycleAdapterNews(news, R.layout.recycler_view_news_item, new RecycleAdapterNews.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(getActivity(), name + " - " + position, Toast.LENGTH_LONG).show();
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private List<String> getAllNews() {
        return new ArrayList<String>() {{
            add("La app Comuni a comenzado su desarrollo con exito");
            add("La primera etapa ha concluido");
            add("Continua el desarrollo de las secciones");
            add("Estamos cerca de la fecha de entrega, a ponerle garra");
        }};
    }
}
