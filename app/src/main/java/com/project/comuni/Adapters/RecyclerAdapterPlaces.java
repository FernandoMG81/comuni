package com.project.comuni.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.InnerNoticiasFragment;
import com.project.comuni.Fragments.InnerPlacesFragment;
import com.project.comuni.Models.Post;
import com.project.comuni.R;

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
            holder.Descripcion.setText(posts.get(position).getTexto());

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
