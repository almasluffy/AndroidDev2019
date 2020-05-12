package com.example.viewpagerkotlin.presentation.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.viewpagerkotlin.R
import com.example.viewpagerkotlin.presentation.post.list.PostListFragment

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), PostListFragment.PostListFragmentListener{

    private val textView: TextView? = null
    private var amount_of_likes: TextView? = null
    private var likes = 0
    private var dopLikes = 0


    private val name: String? = null

    fun SecondFragment() { // Required empty public constructor
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amount_of_likes = view.findViewById(R.id.textView2)
        if (savedInstanceState != null) {
            dopLikes = savedInstanceState.getInt("likes")
        }
        //        mTextViewCounter = view.findViewById(R.id.textViewSumLikes);
//        if (savedInstanceState != null) {
//            mCounter = savedInstanceState.getInt("count");
//            mTextViewCounter.setText(String.valueOf(mCounter));
//        }
    }

    override fun counterResult(count: String?) {
        likes = dopLikes + likes
        likes = Integer.valueOf(count!!)
        likes = dopLikes + likes
        amount_of_likes!!.text = "Number of Likes: $likes"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("likes", likes)
    }


}
