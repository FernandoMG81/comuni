package com.project.comuni.Fragments.Espacios.ABMs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Espacios.PostsFragment;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Post;
import com.project.comuni.Models.Tag;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.PostService;
import com.project.comuni.Servicios.TagService;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private TextView textEtiquetas;
    private TextView titulo;
    private TextView descripcion;
    private Button submit;
    private Spinner tagSpinner;

    public CreatePostFragment(){
        this.tags.add(new Go<Tag>(new Tag(espacio,"Sin Etiqueta","FFFFFF","FFFFFF")));
    }

    private void getData() {
        Bundle bundle = getArguments();
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
       // this.tags.add(new Go<Tag>(new Tag(espacio,"Sin Etiqueta","FFFFFF","FFFFFF")));
    }

    private void setLayoutReference(View view){
        titulo = view.findViewById(R.id.CreatePostTitulo);
        descripcion = view.findViewById(R.id.CreatePostDescripción);
        submit = view.findViewById(R.id.CreatePostSubmit);
        tagSpinner = view.findViewById(R.id.tagSpinner);
        textEtiquetas = view.findViewById(R.id.CreatePostTextSpinner);

        tagSpinner.setVisibility(View.GONE);
        textEtiquetas.setVisibility(View.GONE);
    }

    private void cuestionarioAObjeto(){
        post.getObject().setTitulo(titulo.getText().toString());
        post.getObject().setTexto(descripcion.getText().toString());

        if(!tag.getObject().getText().equals("Sin Etiqueta")) {
            post.getObject().setTags(tag);
        }
        else{
            tag.getObject().setText("");
            post.getObject().setTags(tag);
        }

        post.getObject().setUsuario(new Go<>(usuario));
        post.getObject().setEspacio(new Go<>(espacio));
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        date.getTime();
        post.getObject().setCreated(sdf.format(date));
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
                                        goToPosts();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void goToPosts(){
        AppCompatActivity activity = (MainActivity) this.getContext();
        Fragment myFragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",usuario);
        args.putSerializable("espacioActual",espacio);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }

    private void setRecyclerTags(){

        ArrayAdapter<Go<Tag>> spinnerAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, tags);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(spinnerAdapter);
        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tag = (Go<Tag>) adapterView.getSelectedItem();

                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        getData();
        setLayoutReference(view);

        new TagService().getAllFromEspacios(espacio)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        tags.clear();
                        tag.setObject(new Tag(espacio,"Sin Etiqueta","FFFFFF","FFFFFF"));
                        tags.add(tag);
                        for (DataSnapshot x: snapshot.getChildren())
                        {
                            Go<Tag> tagx = new Go<>(new Tag());
                            tagx.setKey(x.getKey());
                            tagx.setObject(x.getValue(tagx.getObject().getClass()));
                            tags.add(tagx);
                        }

                        if (tags.size() > 1) {
                            tagSpinner.setVisibility(View.VISIBLE);
                            textEtiquetas.setVisibility(View.VISIBLE);
                            setRecyclerTags();
                        }
                        setBoton();
                    }
                });


        return view;
    }
}
