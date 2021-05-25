package com.puput.mymoviesubtiga.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.puput.mymoviesubtiga.ui.favorite.movie.FavoriteMovieFragment
import com.puput.mymoviesubtiga.ui.favorite.tvshow.FavoriteTvShowFragment

class FavoritePagerAdapter(fragmentActivity: AppCompatActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavoriteMovieFragment()
            1 -> fragment = FavoriteTvShowFragment()
        }
        return fragment as Fragment
    }

}

