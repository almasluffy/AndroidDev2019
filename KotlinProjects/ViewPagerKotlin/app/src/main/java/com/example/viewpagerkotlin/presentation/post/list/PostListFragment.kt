package com.example.viewpagerkotlin.presentation.post.list


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpagerkotlin.R
import com.example.viewpagerkotlin.models.Post
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class PostListFragment : Fragment(), PostAdapter.ItemClickListener{

    private var listener: PostListFragmentListener? = null
    private var recyclerView: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener =  context as PostListFragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_post_list, container, false)
        recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        val posts: ArrayList<Post> = ArrayList<Post>()
        val text = "This is my new car!!! How it's for you?"
        for (i in 1..1000) {
            posts.add(Post("Post $i", text))
        }
        val postAdapter = PostAdapter(posts, this as PostAdapter.ItemClickListener)
        recyclerView!!.adapter = postAdapter
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onItemClick(counter: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        listener!!.counterResult(counter)
    }

    internal interface PostListFragmentListener {
        fun counterResult(count: String?)
    }
}
