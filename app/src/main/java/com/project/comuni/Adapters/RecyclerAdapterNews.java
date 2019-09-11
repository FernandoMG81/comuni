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

public class RecyclerAdapterNews extends RecyclerView.Adapter<RecyclerAdapterNews.ViewHolder> {
    private static final String TAG = "RecyclerAdapterNews";


    private ArrayList<String> titulosArray;
    private ArrayList<String> contenidosArray;
    private Context context;

    public RecyclerAdapterNews(ArrayList<String> titulosArray, ArrayList<String> contenidosArray, Context context) {
        this.titulosArray = titulosArray;
        this.contenidosArray = contenidosArray;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_news,null,false);
        RecyclerAdapterNews.ViewHolder holder = new RecyclerAdapterNews.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Titulo.setText(titulosArray.get(position));
        holder.Contenido.setText(contenidosArray.get(position));
    }

    @Override
    public int getItemCount() {
        return titulosArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Titulo;
        TextView Contenido;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.NewsTitulo);
            Contenido = itemView.findViewById(R.id.NewsContenido);
            RL = itemView.findViewById(R.id.RVNews);
        }
    }
}
