package com.project.comuni.Fragments.Mensajes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.comuni.Activities.ListadoUsuariosActivity;
import com.project.comuni.Models.Mensaje;
import com.project.comuni.Servicios.MensajeService;

import com.project.comuni.R;
import com.project.comuni.Adapters.Mensajes.RecyclerAdapterMessages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.project.comuni.Utils.Util.filtrarString;

public class MessagesFragment extends Fragment {
    //Variables Locales
    String searchText = "";

    //Variables Datos
    private ArrayList<Mensaje> mensajes = new ArrayList<>();
    private ArrayList<Mensaje> mensajesAMostrar = new ArrayList<>();

    //Layout
    private Button botonNuevoMensaje;
    private RecyclerView recyclerView;
    private EditText search;

    private void getdata(){
        MensajeService mensajeService = new MensajeService();
        mensajes = mensajeService.filterMensajesPorNVueltas(4);
    }

    private void setLayoutReferences(View view){
        recyclerView = view.findViewById(R.id.RVMesages);
        botonNuevoMensaje = view.findViewById(R.id.addBtnMensaje);
        search = view.findViewById(R.id.MessagesSearch);
    }

    private void setSearch(){
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = editable.toString();
                filterData();
                setRecyclerMensajes();
            }
        });
    }

    private void filterData(){
        mensajesAMostrar.clear();
        for (Mensaje mensaje : mensajes){
            if (filtrarString(mensaje.getTexto(),searchText) ||
                    filtrarString(mensaje.getEmisor().getNombre() + " " + mensaje.getEmisor().getApellido(), searchText) ||
                    filtrarString(mensaje.getReceptor().getNombre() + " " + mensaje.getReceptor().getApellido(), searchText)
            ){
              mensajesAMostrar.add(mensaje);
            }
        }
    }

    private void setRecyclerMensajes(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterMessages adapter = new RecyclerAdapterMessages(mensajesAMostrar, this.getContext());
        recyclerView.setAdapter(adapter);
    }

    private void addButton(){
        botonNuevoMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListadoUsuariosActivity.class);
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        getdata();
        setLayoutReferences(view);
        setSearch();
        filterData();
        setRecyclerMensajes();
        addButton();

        return view;
    }



}
