package com.puput.mymoviesubtiga.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.puput.mymoviesubtiga.ui.movie.FragmentMovie
import com.puput.mymoviesubtiga.ui.tvshow.FragmentTvShow

class SectionPageAdapter(fragmentActivity: AppCompatActivity) :
    FragmentStateAdapter(
        fragmentActivity
    ) {


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentMovie()
            1 -> fragment = FragmentTvShow()
        }
        return fragment as Fragment
    }
}