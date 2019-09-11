package com.project.comuni.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.comuni.Models.Mensaje;
import com.project.comuni.Servicios.MensajeService;

import com.project.comuni.R;
import com.project.comuni.RecyclerAdapterMessages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesFragment extends Fragment {

    private MensajeService mensajeService;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messages, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.RVMesages);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try{
            ArrayList<String> contactos = new ArrayList<>();
            ArrayList<String> mensajes = new ArrayList<>();

            contactos.add("Alumno 1");
            mensajes.add("mensaje del alumno 1");

            contactos.add("Alumno 2");
            mensajes.add("mensaje del alumno 2");

            contactos.add("Alumno 3");
            mensajes.add("mensaje del alumno 3");

            contactos.add("Alumno 4");
            mensajes.add("mensaje del alumno 4");

        RecyclerAdapterMessages adapter = new RecyclerAdapterMessages(mensajes, contactos, this.getContext());

        recyclerView.setAdapter(adapter);

        return view;} catch (Exception e) {
           e.printStackTrace();
       }
       return view;
    }


}
