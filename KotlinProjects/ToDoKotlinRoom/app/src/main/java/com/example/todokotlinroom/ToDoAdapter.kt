package com.example.todokotlinroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ToDoAdapter(private val onItemClickListener: OnItemClickListener?) :
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder?>() {
    private var toDoList: MutableList<ToDo> = ArrayList()
    fun setToDoList(toDoList: MutableList<ToDo>) {
        this.toDoList = toDoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return ToDoViewHolder(view)
    }




    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(toDoList[position])
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun addAll(list: Collection<ToDo>) {
        toDoList.clear()
        toDoList.addAll(list)
        notifyDataSetChanged()
    }

    fun addItem(item: ToDo) {
        toDoList.add(0, item)
        notifyItemInserted(0)
    }

    fun updateItem(position: Int, item: ToDo) {
        toDoList[position] = item
        notifyItemChanged(position)
    }

    inner class ToDoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val goalName: TextView
        private val goalDesc: TextView
        private val goalDate: TextView
        fun bind(item: ToDo) {
            goalName.setText(item.name)
            goalDate.setText(item.date.toString())
            if (item.description.length >= 10) {
                val toShow: String = item.description.substring(0, 10)
                goalDesc.text = "$toShow........."
            } else {
                goalDesc.setText(item.description)
            }
            itemView.setOnClickListener(View.OnClickListener {
                onItemClickListener?.onItemClick(
                    getAdapterPosition(),
                    item
                )
            })
        }

        init {
            goalName = itemView.findViewById(R.id.goalName)
            goalDesc = itemView.findViewById(R.id.goalDesc)
            goalDate = itemView.findViewById(R.id.goalDate)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: ToDo?)
    }

}