package com.project.comuni.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Adapters.RecyclerAdapterMessages;
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

import java.io.Serializable;
import java.util.ArrayList;

public class PlacesFragment extends Fragment {

    private ArrayList<Post> posts = new ArrayList<>();
    private PostService postService = new PostService();

    private Espacio espacioActual = new Espacio();

    private EspacioService espacioService = new EspacioService();

    private Spinner spinner;
    private RecyclerView recyclerView;
    private Button newPlaceButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_places, container, false);

        recyclerView = view.findViewById(R.id.RVPlaces);
        spinner = view.findViewById(R.id.PlacesSpinner);
        newPlaceButton = view.findViewById(R.id.PlacesButton);

        ArrayAdapter<Espacio> spinnerAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, espacioService.getEspacios());

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                espacioActual = (Espacio) adapterView.getSelectedItem();

                posts = postService.filterByEspacioId(espacioActual.getId());

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                RecyclerAdapterPlaces adapter = new RecyclerAdapterPlaces(posts, getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        return view;
    }



    public void getSelectedEspacio(View view){
        Espacio espacio = (Espacio) spinner.getSelectedItem();
    }
}
