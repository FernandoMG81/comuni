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
    Go<Usuario> usuario = new Go<>(new Usuario());
    Map<String, Usuario> x = new HashMap<>();
    // Layout
    private EditText search;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private Button newPlaceButton;

    public void setLayoutReferences(View v){
        search = v.findViewById(R.id.NewsSearch);
        recyclerView = v.findViewById(R.id.RVPlaces);
        spinner = v.findViewById(R.id.PlacesSpinner);
        newPlaceButton = v.findViewById(R.id.PlacesButton);
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

                                }

                            });
                }
                //filterData();
                setRecycler();
                setAddButton();
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

    private void filterData(){

        postsAMostrar.clear();
        for (Go<Post> post: posts){
            if (post.getObject().getEspacio().getKey() == espacioActual.getKey()){
                if (filtrarString(post.getObject().getTitulo(), searchText) ||
                    filtrarString (post.getObject().getTexto(), searchText))
                {
                    postsAMostrar.add(post);
                }
            }
        }
    }

    public void crear() {
        Go<Post> postx = new Go<>(new Post());
        postx.getObject().setEspacio(espacioActual);
        postx.getObject().setTitulo("Amigoooo");
        postx.getObject().setTexto("Como va?");
        new PostService(postx).create();

        /*x.put(usuario.getKey(), usuario.getObject());
        espacioActual.setObject(new Espacio());
        espacioActual.getObject().setNombre("asd");
        espacioActual.getObject().setAdministradores(x);
        new EspacioService(espacioActual).create();*/
    }

    private void setAddButton(){
        newPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crear();
              /*  AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new CreatePostFragment();
                Bundle args = new Bundle();
                args.putSerializable("espacioActual", espacioActual);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
*/
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);

        setLayoutReferences(view);
        usuario.setKey("11OgjOiKgFV9dHWoLLh07PK7lyw2");

        new UsuarioService(usuario).getObject()
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        usuario.setObject(snapshot.getValue(usuario.getObject().getClass()));
                        Toast.makeText(view.getContext(), usuario.getObject().getNombre(), Toast.LENGTH_SHORT).show();

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
