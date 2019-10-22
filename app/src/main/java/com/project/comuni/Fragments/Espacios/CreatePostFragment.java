package com.project.comuni.Fragments.Espacios;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterTags;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Post;
import com.project.comuni.R;
import com.project.comuni.Servicios.TagService;

public class CreatePostFragment extends Fragment {

    //Db
    private FirebaseStorage dbF;

    //Variables
    private  TagService tagService = new TagService();
    private Espacio espacio;

    //Layout
    private TextView titulo;
    private TextView descripcion;
    private RecyclerView recyclerView;
    private Button submit;

    private void getData() {
        Bundle bundle = getArguments();
        this.espacio = (Espacio) bundle.getSerializable("espacioActual");
    }

    private void setLayoutReference(View view){
        titulo = view.findViewById(R.id.CreatePostTitulo);
        descripcion = view.findViewById(R.id.CreatePostDescripción);
        submit = view.findViewById(R.id.CreatePostSubmit);
        recyclerView = view.findViewById(R.id.RVTags);
    }

    private void setBoton(){
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Post PostAGuardar = new Post();
                PostAGuardar.setTitulo(titulo.getText().toString());
                PostAGuardar.setTexto(descripcion.getText().toString());

                if (PostAGuardar.getTitulo() != null & PostAGuardar.getTexto() != null){
                    Toast.makeText(getContext(),"Aca funcion grabar datos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setRecyclerTags(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterTags adapter = new RecyclerAdapterTags(this.tagService.filterTagsByEspacioId(espacio.getId()), this.getContext());
        recyclerView.setAdapter(adapter);
    }

    private void GuardarDatos(){
//        dbF = FirebaseStorage.getInstance();
//        FirebaseDatabase
//                .getInstance()
//                .getReference(NODO_Espacios)
//                .child(espacio.getId())
//                .addChildEventListener(new ChildEventListener() {
//                //asd
//                }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        getData();
        setLayoutReference(view);
        setRecyclerTags();
        setBoton();

        return view;
    }



}
