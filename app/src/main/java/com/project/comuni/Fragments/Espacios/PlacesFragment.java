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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.Espacios.RecyclerAdapterPlaces;
import com.project.comuni.Fragments.Espacios.CreatePostFragment;
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

public class PlacesFragment extends Fragment {

    //Variables Datos
    private ArrayList<Go<Post>> posts = new ArrayList<>();
    private ArrayList<Go<Post>> postsAMostrar = new ArrayList<>();
    private ArrayList<Go<Espacio>> espacios = new ArrayList<>();

    //Variables Filtrado
    private String searchText = "";
    private Go<Espacio> espacioActual = new Go<>();
    private Go<Usuario> usuario = new Go<>(new Usuario());
    private Map<String, Usuario> x = new HashMap<>();
    // Layout
    private EditText search;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private Button newEspacioButton;
    private Button newPostButton;
    private Button newTagButton;

    public void setLayoutReferences(View v){
        search = v.findViewById(R.id.NewsSearch);
        recyclerView = v.findViewById(R.id.RVPlaces);
        spinner = v.findViewById(R.id.PlacesSpinner);
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

    private void setSpinnerEspacios(){
        ArrayAdapter<Go<Espacio>> spinnerAdapter = new ArrayAdapter<>(
                this.getContext(), android.R.layout.simple_spinner_item, espacios);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                espacioActual = (Go<Espacio>) adapterView.getSelectedItem();
                if (espacioActual.getKey() != null) {

                    setAddPostButton();
                    if(usuario.getObject().administrador(espacioActual.getKey())){
                        setAddTagButton();
                    }

                    new PostService().getAllFromEspacios(espacioActual)
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
                                    filterData();
                                    setRecycler();

                                }

                            });
                }

                //setAddButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapterPlaces adapter = new RecyclerAdapterPlaces(postsAMostrar, getContext());
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
                args.putSerializable("espacioActual", espacioActual);
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
                args.putSerializable("espacioActual", espacioActual);
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
                args.putSerializable("espacioActual", espacioActual);
                args.putSerializable("usuario",usuario);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);

        setLayoutReferences(view);
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

                        setAddEspacioButton();
                        new EspacioService().getAllFromUsuario(usuario)
                                .addValueEventListener(
                                        new ValueEventListener() {

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }

                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                for (DataSnapshot x : snapshot.getChildren()) {
                                                    Go<Espacio> aux = new Go<>(new Espacio());
                                                    aux.setKey(x.getKey());
                                                    aux.setObject(x.getValue(aux.getObject().getClass()));
                                                    espacios.add(aux);
                                                }


                                                setSearch();
                                                if (espacios.size() > 0) {
                                                    setSpinnerEspacios();
                                                }

                                            }
                                        });
                    }
                });
        return view;
    }

}
