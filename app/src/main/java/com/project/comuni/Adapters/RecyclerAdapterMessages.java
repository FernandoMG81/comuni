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

import com.project.comuni.R;

import java.util.ArrayList;

public class RecyclerAdapterMessages extends RecyclerView.Adapter<RecyclerAdapterMessages.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private ArrayList<String> mensajesArray;
    private ArrayList<String> contactosArray;
    private Context context;

    public RecyclerAdapterMessages(ArrayList<String> mensajesArray, ArrayList<String> contactosArray, Context context) {
        this.mensajesArray = mensajesArray;
        this.contactosArray = contactosArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_messages,null,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

            holder.Contacto.setText(contactosArray.get(position));
            holder.Mensaje.setText(mensajesArray.get(position));

    }

    @Override
    public int getItemCount() {
        return contactosArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Contacto;
        TextView Mensaje;
        RelativeLayout RL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Contacto = itemView.findViewById(R.id.MessagesContacto);
            Mensaje = itemView.findViewById(R.id.MessagesMensaje);
            RL = itemView.findViewById(R.id.RVMesages);
        }
    }
}
