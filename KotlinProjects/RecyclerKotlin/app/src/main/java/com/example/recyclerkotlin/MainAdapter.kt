package com.example.recyclerkotlin

import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(var posts: List<Post>,val callback: Callback) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.for_post, parent, false))

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(posts[position])
        holder.myimg.visibility = View.VISIBLE
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.postTitle)
        private val description = itemView.findViewById<TextView>(R.id.postDescription)
        val myimg = itemView.findViewById<ImageView>(R.id.imgView_postPic)

        private val share = itemView.findViewById<ImageView>(R.id.shareImg)

        fun bind(post: Post) {
            title.text = post.title
            description.text = post.description.take(40) + "..."

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(posts[adapterPosition])
            }

            share.setOnClickListener{
                val shareBody = post.title + "\n" + post.description
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
               // startActivity(Intent.createChooser(sharingIntent, "send"))

            }


        }
    }

    interface Callback {
        fun onItemClicked(post: Post)
    }

}