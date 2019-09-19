package com.project.comuni.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.InnerNoticiasFragment;
import com.project.comuni.Fragments.InnerPlacesFragment;
import com.project.comuni.Models.Post;
import com.project.comuni.R;

import org.w3c.dom.Text;

import static com.project.comuni.Utils.Util.truncate;

import java.util.ArrayList;

public class RecyclerAdapterPlaces extends RecyclerView.Adapter<RecyclerAdapterPlaces.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "RecyclerAdapterMessages";

    private View.OnClickListener listener;
    private ArrayList<Post> posts;
    private Context context;

    public RecyclerAdapterPlaces(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
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
        Log.d(TAG, "onBindViewHolder: ");

            holder.Titulo.setText(posts.get(position).getTitulo());
            holder.Tag.setText(posts.get(position).getTag().getText());
            holder.Tag.setBackgroundColor(Color.parseColor(posts.get(position).getTag().getBackgroundColor()));
            holder.Tag.setTextColor(Color.parseColor(posts.get(position).getTag().getTextColor()));
            holder.Fecha.setText(posts.get(position).getCreado());
            String TextoTruncado = truncate(posts.get(position).getTexto(),50);
            holder.Descripcion.setText(TextoTruncado);
            Glide.with(this.context).load(posts.get(position).getUsuario().getFoto()).into(holder.FotoUsuario);
            holder.NombreUsuario.setText(posts.get(position).getUsuario().getNombre() + " " + posts.get(position).getUsuario().getApellido());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (MainActivity) view.getContext();
        Fragment myFragment = new InnerPlacesFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Titulo;
        TextView Tag;
        TextView Fecha;
        TextView Descripcion;
        ImageView FotoUsuario;
        TextView NombreUsuario;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Fecha = itemView.findViewById(R.id.PlacesFecha);
            Tag = itemView.findViewById(R.id.Tag);
            Titulo = itemView.findViewById(R.id.PlacesTituloPosteo);
            Descripcion = itemView.findViewById(R.id.PlacesDescripcionPosteo);
            RL = itemView.findViewById(R.id.RVPlaces);
            FotoUsuario = itemView.findViewById(R.id.PlacesFotoUsuario);
            NombreUsuario = itemView.findViewById(R.id.PlacesUsuario);
        }
    }
}
