package com.example.recyclerkotlin

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PostDetailActivity : AppCompatActivity() {

    private lateinit var postNameView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var postImg: ImageView

    private lateinit var share: LinearLayout

    private lateinit var postTitle: String
    private lateinit var postDesc: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        postNameView = findViewById(R.id.postTitle)
        descriptionView = findViewById(R.id.postDescription)

        postImg = findViewById(R.id.imgView_postPic)
        postImg.visibility = View.VISIBLE

        share = findViewById(R.id.sharing)

        val extras = intent.extras
        postTitle = extras!!.getString("postTitle")!!
        postDesc = extras.getString("description")!!

        postNameView.text = postTitle
        descriptionView.text = postDesc

        share.setOnClickListener {
            val shareBody = postTitle + "\n" + postDesc
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "send"))
        }

    }
}
