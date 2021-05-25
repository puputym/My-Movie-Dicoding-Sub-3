package com.puput.mymoviesubtiga.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.databinding.FragmentFavoriteMovieBinding
import com.puput.mymoviesubtiga.ui.detail.DetailMovieActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FavoriteMovieFragment : Fragment() {

    private lateinit var fragmentFavoriteMovieBinding: FragmentFavoriteMovieBinding

    private val viewModel: FavoriteMovieViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoriteMovieBinding =
            FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return fragmentFavoriteMovieBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val favoriteMovieAdapter = FavoriteMovieAdapter()
            fragmentFavoriteMovieBinding.progressBar.visibility = View.VISIBLE
            viewModel.getFavoriteMovie().observe(viewLifecycleOwner) {
                favoriteMovieAdapter.submitList(it)
                with(fragmentFavoriteMovieBinding.rvFavoriteMovie) {
                    layoutManager = GridLayoutManager(context, 2)
                    setHasFixedSize(true)
                    adapter = favoriteMovieAdapter
                    favoriteMovieAdapter.setOnItemClickCallback(object :
                        FavoriteMovieAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: DetailMovie) {
                            val intent = Intent(context, DetailMovieActivity::class.java)
                            intent.putExtra(DetailMovieActivity.EXTRA_DATA, movie.id.toString())
                            intent.putExtra("status", DetailMovieActivity.EXTRA_MOVIE)
                            startActivity(intent)
                        }

                    })
                }
                fragmentFavoriteMovieBinding.progressBar.visibility = View.INVISIBLE
            }

        }
    }
}