package com.project.comuni.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.comuni.Models.Noticia;
import com.project.comuni.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterNewsPrueba extends RecyclerView.Adapter<RecyclerAdapterNewsPrueba.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titulo,noticia;
        ImageView imagenUsuario;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewTituloNoticia);
            noticia = itemView.findViewById(R.id.textViewNoticia);
            imagenUsuario = itemView.findViewById(R.id.imageViewFotoUsuario);
            RL = itemView.findViewById(R.id.RVNews_prueba);
        }
    }

    public List<Noticia> noticiaLista;
    private Context context;

    public RecyclerAdapterNewsPrueba (List<Noticia> noticiaLista,Context context){
        this.noticiaLista = noticiaLista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_news_prueba,null,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titulo.setText(noticiaLista.get(position).getTitulo());
        holder.noticia.setText(noticiaLista.get(position).getTexto());
        holder.imagenUsuario.setImageResource(noticiaLista.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return noticiaLista.size();
    }
}
