package com.project.comuni.Adapters.Espacios;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comuni.Activities.MainActivity;
import com.project.comuni.Fragments.Espacios.ABMs.EditTagFragment;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Tag;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;

import java.util.ArrayList;

public class RecyclerAdapterTags extends RecyclerView.Adapter<RecyclerAdapterTags.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private Go<Espacio> espacio;
    private Go<Usuario> usuario;
    private Boolean administrador;
    private Go<Tag> tag = new Go<>();
    private ArrayList<Go<Tag>> tags;
    private Context context;


    public RecyclerAdapterTags(Context context, Go<Espacio> espacio, Go<Usuario> usuario, ArrayList<Go<Tag>> tags, Boolean administrador) {
        this.context = context;
        this.espacio = espacio;
        this.usuario = usuario;
        this.tags = tags;
        this.administrador = administrador;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tags,null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        holder.Tag.setText(tags.get(position).getObject().getText());
        holder.Tag.setBackgroundColor(Color.parseColor(tags.get(position).getObject().ColorB()));
        holder.Tag.setTextColor(Color.parseColor(tags.get(position).getObject().ColorT()));
        if(administrador) {
            holder.LL.setOnClickListener((view) -> {
                        tag = tags.get(position);
                        Toast.makeText(context, "Editar " + tag.getObject().getText(), Toast.LENGTH_SHORT).show();
                        onClick(holder.LL);
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }


    public void onClick(View view) {
        AppCompatActivity activity = (MainActivity) view.getContext();
        Fragment myFragment = new EditTagFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",usuario);
        args.putSerializable("espacioActual",espacio);
        args.putSerializable("tag",tag);
        myFragment.setArguments(args);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Tag;
        LinearLayout LL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tag = itemView.findViewById(R.id.RecyclerTagsTag);
            LL = itemView.findViewById(R.id.RVTags);
        }
    }
}
