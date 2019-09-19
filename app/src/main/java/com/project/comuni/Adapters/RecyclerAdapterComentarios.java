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

import com.project.comuni.Models.Comentario;
import com.project.comuni.R;

import java.util.ArrayList;

public class RecyclerAdapterComentarios extends RecyclerView.Adapter<RecyclerAdapterComentarios.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private ArrayList<Comentario> comentarios;
    private Context context;

    public RecyclerAdapterComentarios(ArrayList<Comentario> comentarios, Context context) {
        this.comentarios = comentarios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_comentarios,null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        holder.Fecha.setText((comentarios.get(position).getCreated()));
        holder.Usuario.setText(comentarios.get(position).getUsuario().getNombre() + " " +comentarios.get(position).getUsuario().getApellido());
        holder.Comentario.setText(comentarios.get(position).getTexto());

    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Fecha;
        TextView Usuario;
        TextView Comentario;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Fecha = itemView.findViewById(R.id.ComentariosFecha);
            Usuario = itemView.findViewById(R.id.ComentariosContacto);
            Comentario = itemView.findViewById(R.id.ComentariosTexto);
            RL = itemView.findViewById(R.id.RVComentarios);
        }
    }
}
