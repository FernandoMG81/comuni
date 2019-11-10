package com.project.comuni.Adapters.Noticias;

import android.content.Context;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Noticias.InnerNoticiasFragment;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Noticia;
import com.project.comuni.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.project.comuni.Utils.Util.truncate;

public class RecyclerAdapterNews extends RecyclerView.Adapter<RecyclerAdapterNews.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "RecyclerAdapterNews";
    private View.OnClickListener listener;

    private ArrayList<Go<Noticia>> noticias;
    private Go<Noticia> noticia;

    private OnItemListener mOnItemListener;
    private Context context;

    public RecyclerAdapterNews(ArrayList<Go<Noticia>> noticias, Context context) {
        this.noticias = noticias;
        this.context = context;
        //this.mOnItemListener = onItemListener;
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
        String TextoTruncado = truncate(noticias.get(position).getObject().getTexto(),120);
        holder.Titulo.setText(noticias.get(position).getObject().getTitulo());
        holder.Texto.setText(TextoTruncado);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = dateFormat.format(new Date());
        holder.Fecha.setText(fecha);
        holder.Nombre.setText(noticias.get(position).getObject().getNombre());
        Glide.with(context).load(noticias.get(position).getObject().getFotoUrl()).into(holder.imagenUsuario);

        holder.RL.setOnClickListener((view)-> {
            this.noticia = noticias.get(position);
            Toast.makeText(context, noticia.getObject().getTitulo(), Toast.LENGTH_SHORT).show();
            onClick(holder.RL);
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
        args.putSerializable("noticia", this.noticia);
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
        TextView Fecha;
        TextView Nombre;
        ImageView imagenUsuario;
        RelativeLayout RL;
        OnItemListener onItemListener;

        public ViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            FL = itemView.findViewById(R.id.fragment_container);
            Nombre = itemView.findViewById(R.id.RNnombre);
            imagenUsuario = itemView.findViewById(R.id.RNFotoUsuario);
            Titulo = itemView.findViewById(R.id.RNTituloNoticia);
            Texto = itemView.findViewById(R.id.RNNoticia);
            Fecha = itemView.findViewById(R.id.RNFecha);
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
