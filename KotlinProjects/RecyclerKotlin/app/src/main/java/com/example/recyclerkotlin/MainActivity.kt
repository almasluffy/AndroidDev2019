package com.example.recyclerkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val posts = ArrayList<Post>()
        val text = "Pillars are the vertical or near vertical supports of a car's window area or greenhouse—designated respectively as the A, B, C or (in larger cars) D-pillar, moving from front to rear, in profile view."
        for (i in 1..99) {
            posts.add(Post("Post$i", text))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        val myAdapter = MainAdapter(posts, object : MainAdapter.Callback {
            override fun onItemClicked(post: Post) {
                val intent = Intent(this@MainActivity, PostDetailActivity::class.java)
                val bundle = Bundle()

                bundle.putString("postTitle", post.title)
                bundle.putString("description", post.description)

                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
        recyclerView.adapter = myAdapter


    }




}
