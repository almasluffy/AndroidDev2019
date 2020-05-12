package com.example.viewpagerkotlin.presentation.post.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpagerkotlin.R
import com.example.viewpagerkotlin.models.Post
import java.util.*

class PostAdapter(
    posts: ArrayList<Post>,
    itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<PostAdapter.MainViewHolder>() {

    private val posts: ArrayList<Post>
    private val itemClickListener: ItemClickListener
    private var counter = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.for_post, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MainViewHolder,
        position: Int
    ) {
        holder.bind(posts[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class MainViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val postName: TextView
        private val description: TextView
        private val myimg: ImageView
        private val likeImg: ImageView
        private val likeTxt: TextView
        private val shareImg: ImageView
        private val shareTxt: TextView
        fun bind(
            postItem: Post,
            itemClickListener: ItemClickListener
        ) {
            postName.setText(postItem.title)
            description.setText(postItem.description)
            myimg.visibility = View.VISIBLE
            likeTxt.setOnClickListener {
                counter++
                itemClickListener.onItemClick(counter.toString())
            }
            likeImg.setOnClickListener {
                counter++
                itemClickListener.onItemClick(counter.toString())
            }
            shareImg.setOnClickListener { view ->
                val shareBody: String =
                    postItem.title + "\n" + postItem.description
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                view.context.startActivity(Intent.createChooser(sharingIntent, "send"))
            }
            shareTxt.setOnClickListener { view ->
                val shareBody = postName.toString() + "\n" + description
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                view.context.startActivity(Intent.createChooser(sharingIntent, "send"))
            }
        }

        init {
            postName = itemView.findViewById(R.id.postTitle)
            description = itemView.findViewById(R.id.description)
            myimg = itemView.findViewById(R.id.imgView_postPic)
            likeImg = itemView.findViewById(R.id.likeImg)
            likeTxt = itemView.findViewById(R.id.likeTxt)
            shareImg = itemView.findViewById(R.id.shareImg)
            shareTxt = itemView.findViewById(R.id.shareTxt)
        }
    }

    interface ItemClickListener {
        fun onItemClick(counter: String?)
    }

    init {
        this.posts = posts
        this.itemClickListener = itemClickListener
    }
}