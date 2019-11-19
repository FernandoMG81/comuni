package com.project.comuni.Fragments.Espacios;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterPosts;
import com.project.comuni.Fragments.Espacios.ABMs.CreatePostFragment;
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
import java.util.Collections;

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
    private TextView postsVacios;
    private RecyclerView recyclerView;
    private Button ConfigPlacesButton;
    private Button newPostButton;

    //Listeners
    private Query getUsuario;
    private ValueEventListener listenerUsuario;
    private Query getEspacio;
    private ValueEventListener listenerEspacio;
    private Query getPost;
    private ValueEventListener listenerPost;

    private void getData() {
        Bundle bundle = getArguments();
        this.usuario = (Go<Usuario>) bundle.getSerializable("usuario");
        this.espacio = (Go<Espacio>) bundle.getSerializable("espacioActual");
    }

    public void setLayoutReferences(View v){
        search = v.findViewById(R.id.placesNewsSearch);
        postsVacios = v.findViewById(R.id.PlacesPostsVacio);
        recyclerView = v.findViewById(R.id.RVPosts);
        ConfigPlacesButton = v.findViewById(R.id.PlacesButtonConfig);
        newPostButton = v.findViewById(R.id.PlacesButtonPost);

        postsVacios.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
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
        Collections.reverse(postsAMostrar);
    }

    private void setRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterPosts adapter = new RecyclerAdapterPosts(getContext(), usuario, espacio, postsAMostrar);
        recyclerView.setAdapter(adapter);
    }

    private void setAddPostButton(){
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

    private void setAddConfigButton(){
        ConfigPlacesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new ConfigPlaceFragment();
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
        setAddConfigButton();

        usuario = new LoginService().getGoUser();
        getUsuario = new UsuarioService(usuario).getObject();

        getUsuario.addListenerForSingleValueEvent(listenerUsuario =new ValueEventListener() {

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot x: dataSnapshot.getChildren()) {
                            usuario.setObject((x.getValue(usuario.getObject().getClass())));
                        }

                        getEspacio = new EspacioService(espacio).getObject();
                        getEspacio.addListenerForSingleValueEvent(listenerEspacio = new ValueEventListener() {

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                                                espacio.setObject(x.getValue(espacio.getObject().getClass()));

                                        }

                                        getPost = new PostService().getAllFromEspacios(espacio);
                                        getPost.addValueEventListener(listenerPost = new ValueEventListener() {

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }

                                                    @Override
                                                    public void onDataChange(DataSnapshot snapshot) {
                                                        posts.clear();
                                                        for (DataSnapshot x : snapshot.getChildren()) {
                                                            Go<Post> postx = new Go<>(new Post());
                                                            postx.setKey(x.getKey());
                                                            postx.setObject(x.getValue(postx.getObject().getClass()));
                                                            new UsuarioService(postx.getObject().getUsuario())
                                                            .getObject().addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot y: dataSnapshot.getChildren()) {
                                                                        Go<Usuario> usuarioy = new Go<>(y.getKey(), new Usuario());
                                                                        usuarioy.setObject(y.getValue(usuarioy.getObject().getClass()));
                                                                        postx.getObject().setUsuario(usuarioy);
                                                                    }
                                                                    posts.add(postx);

                                                                    setSearch();
                                                                    if (posts.size() > 0) {
                                                                        recyclerView.setVisibility(View.VISIBLE);
                                                                        filterData();
                                                                        setRecycler();
                                                                    }
                                                                    else{
                                                                        postsVacios.setVisibility(View.VISIBLE);
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
        return view;
    }

}
