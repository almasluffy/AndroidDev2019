package com.example.tododatabase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private List<ToDo> toDoList = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public ToDoAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setToDoList(List<ToDo> toDoList) {
        this.toDoList = toDoList;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.bind(toDoList.get(position));
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public void addAll(List<ToDo> list) {
        toDoList.clear();
        toDoList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(ToDo item) {
        toDoList.add(0, item);
        notifyItemInserted(0);


    }

    public void updateItem(int position, ToDo item) {
        toDoList.set(position, item);
        notifyItemChanged(position);
    }


    class ToDoViewHolder extends RecyclerView.ViewHolder {

        private TextView goalName;
        private TextView goalDesc;
        private TextView goalDate;


        ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            goalName = itemView.findViewById(R.id.goalName);
            goalDesc = itemView.findViewById(R.id.goalDesc);
            goalDate = itemView.findViewById(R.id.goalDate);

        }

        public void bind(final ToDo item) {
            goalName.setText(item.getName());
            goalDate.setText(item.getDate().toString());
            if(item.getDescription().length()  >= 10 ){
                String toShow  = item.getDescription().substring(0,10);
                goalDesc.setText(toShow + ".........");
            }
            else{
                goalDesc.setText(item.getDescription());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getAdapterPosition(), item);
                    }
                }
            });
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, ToDo item);
    }
}