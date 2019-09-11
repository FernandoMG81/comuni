package com.project.comuni.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import com.project.comuni.Adapters.RecyclerAdapterNews;
import com.project.comuni.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.RVNews);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try{
            ArrayList<String> titulos = new ArrayList<>();
            ArrayList<String> contenidos = new ArrayList<>();

            titulos.add("La universidad rankea en el puesto 21");
            contenidos.add("Según la organizacion SQL nuestra universidad se encuentra en el 21° puesto " +
                    "gracias a la excelente formación de sus profesores y a su espiritu orientado al emprendedurismo");

            titulos.add("Felicitaciones al equipo de Ecoauto");
            contenidos.add("Cinco alumnos de ingeniería mecánica alcanzaron el 2do puesto en la competencia Ecoauto, por una movilidad sustentable.");

            titulos.add("La universidad sigue creciendo");
            contenidos.add("La universidad ha sido la que más a crecido porcentualmente dentro de Argentina. Entre las razones que nos elijen se encuentran:" +
                    "la excelente formación academica, el prestigio del egresado, la orientación hacia soluciones pŕácticas.");

            titulos.add("Abrió la competencia TuAPP");
            contenidos.add("Ha comenzado la competencia interuniversitaria e internacional donde los alumnos deben crear una aplicación" +
                    "pasando por todas las etapas necesarias para ello, desde la formaciión de la idea, hasta el código mismo.");

            RecyclerAdapterNews adapter = new RecyclerAdapterNews(titulos, contenidos, this.getContext());

            recyclerView.setAdapter(adapter);

            return view;} catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
