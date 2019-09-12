package com.project.comuni.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comuni.R;

import java.util.ArrayList;

public class RecyclerAdapterPlaces extends RecyclerView.Adapter<RecyclerAdapterPlaces.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private ArrayList<String> TitulosArray;
    private ArrayList<String> DescripcionesArray;
    private Context context;

    public RecyclerAdapterPlaces(ArrayList<String> titulosArray, ArrayList<String> descripcionesArray, Context context) {
        this.TitulosArray = titulosArray;
        this.DescripcionesArray = descripcionesArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_messages,null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

            holder.Titulo.setText(TitulosArray.get(position));
            holder.Descripcion.setText(DescripcionesArray.get(position));

    }

    @Override
    public int getItemCount() {
        return TitulosArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Titulo;
        TextView Descripcion;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.PlacesTituloPosteo);
            Descripcion = itemView.findViewById(R.id.PlacesDescripcionPosteo);
            RL = itemView.findViewById(R.id.RVPlaces);
        }
    }
}
