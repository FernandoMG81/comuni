package com.project.comuni.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.comuni.Models.Mensaje;
import com.project.comuni.Servicios.MensajeService;

import com.project.comuni.R;
import com.project.comuni.Adapters.RecyclerAdapterMessages;
import com.project.comuni.Servicios.UsuarioService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesFragment extends Fragment {
    private UsuarioService usuarioService = new UsuarioService();
    private MensajeService mensajeService = new MensajeService();
    private ArrayList<Mensaje> mensajes = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RVMesages);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mensajes = mensajeService.getMensajes();

        RecyclerAdapterMessages adapter = new RecyclerAdapterMessages(mensajes, this.getContext());

        recyclerView.setAdapter(adapter);

       return view;
    }


}
