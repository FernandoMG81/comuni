package com.project.comuni.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.HomeFragment;
import com.project.comuni.Fragments.InnerNoticiasFragment;
import com.project.comuni.Fragments.MessagesFragment;
import com.project.comuni.Fragments.PlacesFragment;
import com.project.comuni.Models.Noticia;
import com.project.comuni.R;

import java.util.ArrayList;

import static com.project.comuni.Utils.Util.truncate;

public class RecyclerAdapterNews extends RecyclerView.Adapter<RecyclerAdapterNews.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "RecyclerAdapterNews";
    private View.OnClickListener listener;

    private ArrayList<Noticia> noticias;
    private OnItemListener mOnItemListener;
    private Context context;
    private String txtPrueba;

    public RecyclerAdapterNews(ArrayList<Noticia> noticias, Context context, OnItemListener onItemListener) {
        this.noticias = noticias;
        this.context = context;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public RecyclerAdapterNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_news,null,false);
        RecyclerAdapterNews.ViewHolder holder = new RecyclerAdapterNews.ViewHolder(view, mOnItemListener);

        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String TextoTruncado = truncate(noticias.get(position).getTexto(),120);
        holder.Titulo.setText(noticias.get(position).getTitulo());
        holder.Texto.setText(TextoTruncado);
        holder.imagenUsuario.setImageResource(noticias.get(position).getImagen());

        holder.RL.setOnClickListener((view)-> {
            onClick(holder.RL);
            Toast.makeText(context, noticias.get(position).getTitulo(), Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }



    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (MainActivity) view.getContext();
        Fragment myFragment = new InnerNoticiasFragment();
        Bundle args = new Bundle();
        args.putString("texto", txtPrueba);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        FrameLayout FL;
        TextView Titulo;
        TextView Texto;
        ImageView imagenUsuario;
        RelativeLayout RL;
        OnItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            FL = itemView.findViewById(R.id.fragment_container);
            imagenUsuario = itemView.findViewById(R.id.imageViewFotoUsuario);
            Titulo = itemView.findViewById(R.id.textViewTituloNoticia);
            Texto = itemView.findViewById(R.id.textViewNoticia);
            RL = itemView.findViewById(R.id.RVNews);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}
