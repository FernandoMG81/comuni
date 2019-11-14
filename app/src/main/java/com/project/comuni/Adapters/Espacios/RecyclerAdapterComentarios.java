package com.project.comuni.Adapters.Espacios;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.comuni.Models.Comentario;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.R;
import com.project.comuni.Servicios.ComentarioService;

import java.util.ArrayList;

public class RecyclerAdapterComentarios extends RecyclerView.Adapter<RecyclerAdapterComentarios.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private Go<Comentario> comentario;
    private ArrayList<Go<Comentario>> comentarios;
    private Context context;

    public RecyclerAdapterComentarios(ArrayList<Go<Comentario>> comentarios, Context context) {
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

        holder.Fecha.setText((comentarios.get(position).getObject().getCreated()));
        holder.Usuario.setText(comentarios.get(position).getObject().getUsuario().getObject().getNombre()
                + " " +comentarios.get(position).getObject().getUsuario().getObject().getApellido());
        holder.Comentario.setText(comentarios.get(position).getObject().getTexto());

        holder.RL.setOnLongClickListener((view -> {
            this.comentario = comentarios.get(position);
            new ComentarioService(comentario).delete()
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context, "El post se borró exitosamente.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context, "No se pudo borrar el post.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            return true;
        }));

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
