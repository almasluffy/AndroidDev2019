package com.example.viewpagerkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.viewpagerkotlin.presentation.post.list.PostListFragment
import com.example.viewpagerkotlin.presentation.profile.ProfileFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class TabActivity : AppCompatActivity(), PostListFragment.PostListFragmentListener {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var my_count = 0
    private var viewPagerAdapter: MainViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        viewPagerAdapter = MainViewPagerAdapter(supportFragmentManager, 1)
        viewPager!!.setAdapter(viewPagerAdapter)
        //tabLayout.setupWithViewPager(viewPager);
        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                tabLayout!!.getTabAt(position)!!.select()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        my_count = savedInstanceState.getInt("cnt")
        val tag = "android:switcher:" + R.id.viewPager.toString() + ":" + 1
        val f: ProfileFragment? =
            supportFragmentManager.findFragmentByTag(tag) as ProfileFragment?
        f!!.counterResult(my_count.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("cnt", my_count)
    }



    override fun counterResult(count: String?) {
        val tag = "android:switcher:" + R.id.viewPager.toString() + ":" + 1
        val fragment: ProfileFragment? =
            supportFragmentManager.findFragmentByTag(tag) as ProfileFragment?
        fragment!!.counterResult(count)
    }

    internal inner class MainViewPagerAdapter(
        fm: FragmentManager,
        behavior: Int
    ) :
        FragmentPagerAdapter(fm, behavior) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                PostListFragment()
            } else {
                ProfileFragment()
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
}
