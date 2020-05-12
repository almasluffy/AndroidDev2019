package com.example.viewpagerkotlin.presentation.post.detail


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.example.viewpagerkotlin.R

/**
 * A simple [Fragment] subclass.
 */
class PostDetailFragment : Fragment() {

    private lateinit var postNameView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var postImg: ImageView

    private lateinit var share: LinearLayout

    private lateinit var postTitle: String
    private lateinit var postDesc: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postNameView = view.findViewById(R.id.postTitle)
        descriptionView = view.findViewById(R.id.description)

        postImg = view.findViewById(R.id.imgView_postPic)
        postImg.visibility = View.VISIBLE

        share = view.findViewById(R.id.sharing)

        postTitle = arguments?.getString("postTitle")!!
        postDesc = arguments?.getString("description")!!

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
