package com.puput.mymoviesubtiga.ui.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow
import com.puput.mymoviesubtiga.databinding.FragmentFavoriteTvShowBinding
import com.puput.mymoviesubtiga.ui.detail.DetailMovieActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FavoriteTvShowFragment : Fragment() {
    private lateinit var fragmentFavoriteTvShowBinding: FragmentFavoriteTvShowBinding

    private val viewModel: FavoriteTvShowViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoriteTvShowBinding =
            FragmentFavoriteTvShowBinding.inflate(inflater, container, false)
        return fragmentFavoriteTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val favoriteTvShowAdapter = FavoriteTvShowAdapter()
            fragmentFavoriteTvShowBinding.progressBar.visibility = View.VISIBLE
            viewModel.getFavoriteTvShow().observe(viewLifecycleOwner) {
                favoriteTvShowAdapter.submitList(it)
                with(fragmentFavoriteTvShowBinding.rvFavoriteTvShow) {
                    layoutManager = GridLayoutManager(context, 2)
                    setHasFixedSize(true)
                    adapter = favoriteTvShowAdapter
                    favoriteTvShowAdapter.setOnItemClickCallbackFavoriteTv(object :
                        FavoriteTvShowAdapter.OnItemClickCallback {
                        override fun onItemClicked(tvshow: DetailTVShow) {
                            val intent = Intent(context, DetailMovieActivity::class.java)
                            Log.d("series", tvshow.id.toString())
                            intent.putExtra(DetailMovieActivity.EXTRA_DATA, tvshow.id.toString())
                            intent.putExtra("status", DetailMovieActivity.EXTRA_TVSHOW)
                            startActivity(intent)
                        }
                    })
                }
                fragmentFavoriteTvShowBinding.progressBar.visibility = View.GONE
            }

        }
    }

}