package com.project.comuni.Fragments;

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

import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.RecyclerAdapterPlaces;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Post;
import com.project.comuni.R;
import com.project.comuni.Servicios.EspacioService;
import com.project.comuni.Servicios.PostService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.project.comuni.Utils.Util.filtrarString;

public class PlacesFragment extends Fragment {

    //Variables Datos
    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Post> postsAMostrar = new ArrayList<>();
    private ArrayList<Espacio> espacios = new ArrayList<>();

    //Variables Filtrado
    private String searchText = "";
    private Espacio espacioActual = new Espacio();

    // Layout
    private EditText search;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private Button newPlaceButton;

    public void setLayoutReferences(View view){
        search = view.findViewById(R.id.NewsSearch);
        recyclerView = view.findViewById(R.id.RVPlaces);
        spinner = view.findViewById(R.id.PlacesSpinner);
        newPlaceButton = view.findViewById(R.id.PlacesButton);
    }

    private void getData(){
        PostService postService = new PostService();
        posts = postService.getPosts();

        EspacioService espacioService = new EspacioService();
        espacios =  espacioService.getEspacios();


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
        ArrayAdapter<Espacio> spinnerAdapter = new ArrayAdapter<>(
                this.getContext(), android.R.layout.simple_spinner_item, espacios);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                espacioActual = (Espacio) adapterView.getSelectedItem();
                filterData();
                setRecycler();
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
        for (Post post: posts){
            if (post.getEspacio().getId() == espacioActual.getId()){
                if (filtrarString(post.getTitulo(), searchText) ||
                    filtrarString (post.getTexto(), searchText))
                {
                    postsAMostrar.add(post);
                }
            }
        }
    }

    private void setAddButton(){
        newPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (MainActivity) view.getContext();
                Fragment myFragment = new CreatePostFragment();
                Bundle args = new Bundle();
                args.putSerializable("espacioActual", espacioActual);
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);

        getData();
        setLayoutReferences(view);
        setSpinnerEspacios();
        setSearch();
        setAddButton();
        return view;
    }
}
