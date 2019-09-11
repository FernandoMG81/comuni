package com.project.comuni.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.comuni.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapterNews extends RecyclerView.Adapter<RecycleAdapterNews.ViewHolder>{

    private List<String> news;
    private int layout;
    private OnItemClickListener itemClickListener;

    public RecycleAdapterNews(List<String> news, int layout, OnItemClickListener itemClickListener){
        this.news = news;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_news_item, null, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(news.get(position), itemClickListener);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewNewsItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewNewsItem = itemView.findViewById(R.id.textViewNewsItem);

        }
    public void bind(final String news, final OnItemClickListener listener){
            this.textViewNewsItem.setText(news);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(news, getAdapterPosition());
                }
            });
    }

    }

    public interface OnItemClickListener {
        void onItemClick(String name, int position);
    }
}
