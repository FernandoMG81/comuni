package com.project.comuni.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comuni.Fragments.PlacesFragment;
import com.project.comuni.Models.Noticia;
import com.project.comuni.R;

import java.util.ArrayList;

import static com.project.comuni.Utils.Util.truncate;

public class RecyclerAdapterNews extends RecyclerView.Adapter<RecyclerAdapterNews.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "RecyclerAdapterNews";

    private View.OnClickListener listener;
    private ArrayList<Noticia> noticias;
    private Context context;

    public RecyclerAdapterNews(ArrayList<Noticia> noticias, Context context) {
        this.noticias = noticias;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_news,null,false);
        RecyclerAdapterNews.ViewHolder holder = new RecyclerAdapterNews.ViewHolder(view);

        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String TextoTruncado = truncate(noticias.get(position).getTexto(),75);
        holder.Titulo.setText(noticias.get(position).getTitulo());
        holder.Texto.setText(TextoTruncado);
        holder.imagenUsuario.setImageResource(noticias.get(position).getImagen());

        holder.RL.setOnClickListener((view)-> {
            Toast.makeText(context, noticias.get(position).getTitulo(), Toast.LENGTH_SHORT).show();



        });
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    @Override
    public void onClick(View view) {

    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Titulo;
        TextView Texto;
        ImageView imagenUsuario;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenUsuario = itemView.findViewById(R.id.imageViewFotoUsuario);
            Titulo = itemView.findViewById(R.id.textViewTituloNoticia);
            Texto = itemView.findViewById(R.id.textViewNoticia);
            RL = itemView.findViewById(R.id.RVNews);
        }
    }
}
