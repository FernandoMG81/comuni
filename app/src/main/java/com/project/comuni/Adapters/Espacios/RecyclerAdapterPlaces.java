package com.project.comuni.Adapters.Espacios;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Espacios.PostsFragment;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;

import java.util.ArrayList;

public class RecyclerAdapterPlaces extends RecyclerView.Adapter<RecyclerAdapterPlaces.ViewHolder> implements View.OnClickListener {

    private ArrayList<Go<Espacio>> espacios;
    private Go<Espacio> espacio = new Go<>();
    private Go<Usuario> usuario;

    private View.OnClickListener listener;
    private Context context;

    public RecyclerAdapterPlaces(Context context, ArrayList<Go<Espacio>> espacios, Go<Usuario> usuario ) {
        this.espacios = espacios;
        this.context = context;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_places,null,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (espacios.size() > 0) {
            holder.Nombre.setText(espacios.get(position).getObject().getNombre());
            holder.Descripcion.setText(espacios.get(position).getObject().getDescripcion());
            holder.RL.setOnClickListener((view) -> {
                this.espacio = espacios.get(position);
                Toast.makeText(context, espacio.getObject().getNombre(), Toast.LENGTH_SHORT).show();
                onClick(holder.RL);
            });
        }
    }

    @Override
    public int getItemCount() {
        return espacios.size();
    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (MainActivity) view.getContext();
        Fragment myFragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",usuario);
        args.putSerializable("espacioActual",espacio);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Nombre;
        TextView Descripcion;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.RVPlacesNombre);
            Descripcion = itemView.findViewById(R.id.RVPlacesDescripcion);
            RL = itemView.findViewById(R.id.placesRV);
        }
    }
}
