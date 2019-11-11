package com.project.comuni.Fragments.Espacios;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterPosts;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Post;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Servicios.PostService;
import com.project.comuni.Servicios.UsuarioService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.project.comuni.Utils.Util.filtrarString;

public class PostsFragment extends Fragment {

    //Variables Datos
    private Go<Espacio> espacio = new Go<>();
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private ArrayList<Go<Post>> posts = new ArrayList<>();


    //Variables Filtrado
    private String searchText = "";
    private ArrayList<Go<Post>> postsAMostrar = new ArrayList<>();

    // Layout
    private EditText search;
    private RecyclerView recyclerView;
    private Button newEspacioButton;
    private Button newPostButton;
    private Button newTagButton;

    private void getData() {
        Bundle bundle = getArguments();
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
    }

    public void setLayoutReferences(View v){
        search = v.findViewById(R.id.NewsSearch);
        recyclerView = v.findViewById(R.id.RVPosts);
        newEspacioButton = v.findViewById(R.id.PlacesButtonEspacio);
        newEspacioButton.setVisibility(View.GONE);
        newPostButton = v.findViewById(R.id.PlacesButtonPost);
        newPostButton.setVisibility(View.GONE);
        newTagButton = v.findViewById(R.id.PlacesButtonTag);
        newTagButton.setVisibility(View.GONE);
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
                setRecycler();
            }
        });
    }

    private void filterData(){

        postsAMostrar.clear();
        for (Go<Post> post: posts){
            if (filtrarString(post.getObject().getTitulo(), searchText) ||
                    filtrarString (post.getObject().getTexto(), searchText))
            {
                postsAMostrar.add(post);

            }
        }
    }

    private void setRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterPosts adapter = new RecyclerAdapterPosts(postsAMostrar, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void setAddTagButton(){
        newTagButton.setVisibility(View.VISIBLE);
        newTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new CreateTagFragment();
                Bundle args = new Bundle();
                args.putSerializable("espacioActual", espacio);
                args.putSerializable("usuario",usuario);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    private void setAddEspacioButton(){
        newEspacioButton.setVisibility(View.VISIBLE);
        newEspacioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new CreateEspacioFragment();
                Bundle args = new Bundle();
                args.putSerializable("espacioActual", espacio);
                args.putSerializable("usuario",usuario);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    private void setAddPostButton(){
        newPostButton.setVisibility(View.VISIBLE);
        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new CreatePostFragment();
                Bundle args = new Bundle();
                args.putSerializable("espacioActual", espacio);
                args.putSerializable("usuario",usuario);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        getData();
        setLayoutReferences(view);
        setAddPostButton();
        if(usuario.getObject().administrador(espacio.getKey())){
        setAddTagButton();
        }
        usuario = new LoginService().getGoUser();
        new UsuarioService(usuario).getObject()
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot x: dataSnapshot.getChildren()) {
                            usuario.setObject((x.getValue(usuario.getObject().getClass())));
                        }


                        for (Map.Entry<String, Espacio> x :usuario.getObject().getAdministradores().entrySet()) {
                            if(x.getKey().equals(espacio.getKey())){
                                setAddEspacioButton();
                                setAddTagButton();
                                break;
                            }
                        }

                        new PostService().getAllFromEspacios(espacio)
                                .addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }

                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for (DataSnapshot x : snapshot.getChildren()) {
                                            Go<Post> postx = new Go<>(new Post());
                                            postx.setKey(x.getKey());
                                            postx.setObject(x.getValue(postx.getObject().getClass()));
                                            posts.add(postx);
                                        }
                                        setSearch();
                                        if (posts.size() > 0) {
                                            filterData();
                                            setRecycler();
                                        }

                                    }

                                });
                    }
                });
        return view;
    }

}
