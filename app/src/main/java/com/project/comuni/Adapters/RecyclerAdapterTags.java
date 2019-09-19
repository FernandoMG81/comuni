package com.project.comuni.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comuni.Models.Comentario;
import com.project.comuni.Models.Tag;
import com.project.comuni.R;

import java.util.ArrayList;

public class RecyclerAdapterTags extends RecyclerView.Adapter<RecyclerAdapterTags.ViewHolder> {
    private static final String TAG = "RecyclerAdapterMessages";

    private ArrayList<Tag> tags;
    private Context context;

    public RecyclerAdapterTags(ArrayList<Tag> tags, Context context) {
        this.tags = tags;
        this.context = context;
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

        holder.Tag.setText(tags.get(position).getText());
        holder.Tag.setBackgroundColor(Color.parseColor(tags.get(position).getBackgroundColor()));
        holder.Tag.setTextColor(Color.parseColor(tags.get(position).getTextColor()));

    }

    @Override
    public int getItemCount() {
        return tags.size();
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
