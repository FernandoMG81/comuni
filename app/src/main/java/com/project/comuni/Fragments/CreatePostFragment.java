package com.project.comuni.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.comuni.Adapters.RecyclerAdapterTags;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Noticia;
import com.project.comuni.Models.Post;
import com.project.comuni.Models.Tag;
import com.project.comuni.R;
import com.project.comuni.Servicios.NoticiaService;
import com.project.comuni.Servicios.TagService;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CreatePostFragment extends Fragment {

    private  TagService tagService = new TagService();
    private Espacio espacio;

    private TextView titulo;
    private TextView descripcion;
    private Button submit;

    private FirebaseStorage dbF;

    public void setLayout(View view){
        titulo = view.findViewById(R.id.CreatePostTitulo);
        descripcion = view.findViewById(R.id.CreatePostDescripción);
        submit = view.findViewById(R.id.CreatePostSubmit);
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

    public void setEspacio() {
        Bundle bundle = getArguments();
        this.espacio = (Espacio) bundle.getSerializable("espacioActual");
    }

//    public void GuardarDatos(){
//        dbF = FirebaseStorage.getInstance();
//        FirebaseDatabase
//                .getInstance()
//                .getReference(NODO_Espacios)
//                .child(espacio.getId())
//                .addChildEventListener(new ChildEventListener() {
//                //asd
//                }
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setEspacio();
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        setLayout(view);

        RecyclerView recyclerView = view.findViewById(R.id.RVTags);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerAdapterTags adapter = new RecyclerAdapterTags(this.tagService.filterTagsByEspacioId(espacio.getId()), this.getContext());

        recyclerView.setAdapter(adapter);

        return view;
    }



}
