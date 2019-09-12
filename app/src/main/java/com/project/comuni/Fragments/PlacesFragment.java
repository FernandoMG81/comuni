package com.project.comuni.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.comuni.Adapters.RecyclerAdapterMessages;
import com.project.comuni.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlacesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_places, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.RVPlaces);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<String> descripciones = new ArrayList<>();

        titulos.add("No entiendo el switch");
        descripciones.add("¿Alguno podría explicarme para que sirve? Falte a la clase y ahora no entiendo nada.");

        titulos.add("¿Cuál es la diferencia entre el while y el for?");
        descripciones.add("No entiendo para que sirve el for, si con el while también podrías marcarle la cantidad de vueltas que querés.");

        titulos.add("Repasar el sábado");
        descripciones.add("El sábado voy a ir a estudiar a la facultad para prepararme para el parcial. Si alguno quiere venir estudiamos juntos.");

        titulos.add("Examen el 19/09");
        descripciones.add("Recuerden que el 19 tenemos el parcial. Entra hasta ciclos exactos e inexactos. Obviamente requieren también los conocimientos " +
                "del primer parcial, asi que repasen TODO.");

        RecyclerAdapterMessages adapter = new RecyclerAdapterMessages(descripciones, titulos, this.getContext());

        recyclerView.setAdapter(adapter);

        return view;
    }
}
