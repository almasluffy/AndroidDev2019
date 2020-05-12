package com.example.viewpagerkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.viewpagerkotlin.presentation.post.list.PostListFragment
import com.example.viewpagerkotlin.presentation.profile.ProfileFragment
import org.intellij.lang.annotations.PrintFormat

class MainActivity : AppCompatActivity(), PostListFragment.PostListFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Show fragment in container
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer1, PostListFragment())
            .commit()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer2, ProfileFragment())
            .commit()
    }

    override fun counterResult(count: String?) {
        val fragment: ProfileFragment? =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer2) as ProfileFragment?
        fragment!!.counterResult(count)
    }

}
