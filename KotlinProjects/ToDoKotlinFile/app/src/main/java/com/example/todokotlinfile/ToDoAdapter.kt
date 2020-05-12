package com.example.todokotlinfile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*

class ToDoAdapter(private val onItemClickListener: OnItemClickListener?) :
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    private var toDoList: MutableList<ToDo> = ArrayList()
    fun setToDoList(toDoList: MutableList<ToDo>) {
        this.toDoList = toDoList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_to_do, parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ToDoViewHolder,
        position: Int
    ) {
        holder.bind(toDoList[position])
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun addAll(list: List<ToDo?>?) {
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
        ViewHolder(itemView) {
        private val goalName: TextView
        private val goalDesc: TextView
        private val goalDate: TextView
        fun bind(item: ToDo) {
            goalName.setText(item.name)
            goalDate.setText(item.date.toString())
//            if (item.description!!.length >= 10) {
//                val toShow: String = item.description.substring(0, 10)
//                goalDesc.text = "$toShow........."
//            } else {
//                goalDesc.setText(item.description)
//            }
            goalDesc.setText(item.description)
            itemView.setOnClickListener { onItemClickListener?.onItemClick(adapterPosition, item) }
        }

        init {
            goalName = itemView.findViewById(R.id.textViewName)
            goalDesc = itemView.findViewById(R.id.textViewDescription)
            goalDate = itemView.findViewById(R.id.textViewDate)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: ToDo?)
    }

}