package com.puput.mymoviesubtiga.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.puput.mymoviesubtiga.R
import com.puput.mymoviesubtiga.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFavoriteBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie, R.string.tv_show)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val setViewPager = FavoritePagerAdapter(this)

        val viewPager: ViewPager2 = this.binding.viewPager
        viewPager.adapter = setViewPager
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            this.run {
                tab.text = resources.getString(TAB_TITLES[position])
            }
        }.attach()

        binding.tvFavorite.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_favorite->finish()
        }
    }

}