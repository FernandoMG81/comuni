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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterTags;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Post;
import com.project.comuni.Models.Tag;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.PostService;
import com.project.comuni.Servicios.TagService;

import java.nio.file.attribute.GroupPrincipal;
import java.util.ArrayList;

public class CreatePostFragment extends Fragment {

    //Db
    private FirebaseStorage dbF;

    //Variables
    private ArrayList<Go<Tag>> tags = new ArrayList<>();
    private Go<Tag> tag = new Go<>(new Tag());
    private Go<Espacio> espacio;
    private Go<Post> post = new Go<>(new Post());
    private Go<Usuario> usuario = new Go<>(new Usuario());

    //Layout
    private TextView titulo;
    private TextView descripcion;
    private RecyclerView recyclerView;
    private Button submit;

    private void getData() {
        Bundle bundle = getArguments();
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
    }

    private void setLayoutReference(View view){
        titulo = view.findViewById(R.id.CreatePostTitulo);
        descripcion = view.findViewById(R.id.CreatePostDescripción);
        submit = view.findViewById(R.id.CreatePostSubmit);
        recyclerView = view.findViewById(R.id.RVTags);
    }

    private void cuestionarioAObjeto(){
        post.getObject().setTitulo(titulo.getText().toString());
        post.getObject().setTexto(descripcion.getText().toString());
        //post.getObject().setTags(new Go<>(tag));
        post.getObject().setUsuario(new Go<>(usuario));
        post.getObject().setEspacio(new Go<>(espacio));
    }

    private void setBoton(){
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                cuestionarioAObjeto();

                if(!post.getObject().validar().equals("Ok")){
                    Toast.makeText(getContext(),post.getObject().validar(),Toast.LENGTH_LONG).show();
                }
                else{
                    new PostService(post).create()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "Se creo el espacio", Toast.LENGTH_LONG).show();
                                        goToEspacios();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void goToEspacios(){
        AppCompatActivity activity = (MainActivity) this.getContext();
        Fragment myFragment = new PlacesFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",usuario);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }

    private void setRecyclerTags(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterTags adapter = new RecyclerAdapterTags(tags, this.getContext());
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        getData();
        setLayoutReference(view);

        new TagService().getAllFromEspacios(espacio)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot x: snapshot.getChildren())
                        {
                            Go<Tag> tag = new Go<>(new Tag());
                            tag.setKey(x.getKey());
                            tag.setObject(x.getValue(tag.getObject().getClass()));
                            tags.add(tag);
                        }

                        if (tags.size() > 0) {
                            setRecyclerTags();
                        }
                    }
                });
        setBoton();

        return view;
    }



}
