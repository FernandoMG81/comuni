package com.example.comuni;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterMensajes extends RecyclerView.Adapter<RecyclerAdapterMensajes.ViewHolder>{

    private static final String TAG = "RecyclerAdapterMensajes";

    private ArrayList<String> Contactos = new ArrayList<>();
    private ArrayList<String> Mensajes = new ArrayList<>();
    private ArrayList<String> Context = new ArrayList<>();

    public RecyclerAdapterMensajes(ArrayList<String> contactos, ArrayList<String> mensajes, ArrayList<String> context) {
        Contactos = contactos;
        Mensajes = mensajes;
        Context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_mensajes,parent, false);
        ViewHolder holder = new ViewHolder (view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        holder.ContactoMensajes.setText(Contactos.get(position));
        holder.MensajeMensajes.setText(Mensajes.get(position));
        
    }

    @Override
    public int getItemCount() {
        return Mensajes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       TextView ContactoMensajes;
       TextView MensajeMensajes;
       RelativeLayout RLMensajes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ContactoMensajes = itemView.findViewById(R.id.ContactoMensajes);
            MensajeMensajes = itemView.findViewById(R.id.MensajeMensajes);
            RLMensajes = itemView.findViewById(R.id.RLMensajes);
        }
    }

}

