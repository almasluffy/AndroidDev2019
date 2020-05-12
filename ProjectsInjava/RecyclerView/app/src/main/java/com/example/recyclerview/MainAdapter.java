package com.example.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private ArrayList<PostItem> posts;

    private ItemClickListener itemClickListener;

    public MainAdapter(ArrayList<PostItem> posts, ItemClickListener itemClickListener) {
        super();
        this.posts = posts;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_row_person, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {
        holder.bind(posts.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private TextView postName;
        private TextView description;
        private ImageView image;
        private ImageView profileImage;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            postName = itemView.findViewById(R.id.postName);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageView);
            profileImage = itemView.findViewById(R.id.imageView2);
        }

        public void bind(final PostItem postItem, final ItemClickListener itemClickListener) {
            postName.setText(postItem.getPostName());
            description.setText(postItem.getDescription());
            image.setImageResource(R.drawable.pain);
            profileImage.setImageResource(R.drawable.naruto);
            if(itemClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        itemClickListener.onItemClick(postItem);
                    }
                });
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(PostItem postItem);
    }
}