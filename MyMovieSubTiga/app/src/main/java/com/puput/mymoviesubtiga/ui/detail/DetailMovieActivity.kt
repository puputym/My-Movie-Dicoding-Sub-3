package com.puput.mymoviesubtiga.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.puput.mymoviesubtiga.R
import com.puput.mymoviesubtiga.databinding.ActivityDetailMovieBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailMovieActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel: DetailMovieActivityViewModel by viewModel()
    private var statusFav = false

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w200"
        const val EXTRA_MOVIE = "movie"
        const val EXTRA_TVSHOW = "tvShow"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener(this)
        binding.share.setOnClickListener(this)

        val id = intent.getStringExtra(EXTRA_DATA)

        val status = intent.getStringExtra("status")

        if (status == EXTRA_MOVIE) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                viewModel.getDetailMovie(id.toString())
                    .observe(this@DetailMovieActivity) { detailMovie ->
                        checkFavMovieId(detailMovie.id.toString())
                        title.text = detailMovie.title
                        year.text = detailMovie.releaseDate
                        durasi.text = detailMovie.runtime
                        score.text = detailMovie.voteAverage.toString()
                        tvDesc.text = detailMovie.overview

                        Glide.with(this@DetailMovieActivity)
                            .load(IMAGE_URL + detailMovie.posterPath)
                            .apply(RequestOptions().override(200, 250))
                            .into(imgItemPhoto)

                        binding.toggleFav.setOnClickListener {
                            if (!statusFav) {
                                viewModel.insertFavMovie(detailMovie)
                                Toast.makeText(
                                    applicationContext,
                                    "succsessfull insert",
                                    Toast.LENGTH_SHORT
                                ).show()
                                statusFav = true

                            } else {
                                viewModel.deleteFavMovie(detailMovie)
                                Toast.makeText(
                                    applicationContext,
                                    "succsessfull delete",
                                    Toast.LENGTH_SHORT
                                ).show()
                                statusFav = false
                            }
                            binding.toggleFav.isChecked = statusFav
                        }
                        progressBar.visibility = View.INVISIBLE
                    }
            }
        } else {
            binding.apply {
                viewModel.getDetailTvShow(id.toString())
                    .observe(this@DetailMovieActivity) { detailTvShow ->
                        checkFavTvId(detailTvShow.id.toString())
                        title.text = detailTvShow.name
                        year.text = detailTvShow.firstAirDate
                        durasi.text = detailTvShow.status
                        score.text = detailTvShow.voteAverage.toString()
                        tvDesc.text = detailTvShow.overview

                        Glide.with(this@DetailMovieActivity)
                            .load(IMAGE_URL + detailTvShow.posterPath)
                            .apply(RequestOptions().override(200, 250))
                            .into(imgItemPhoto)

                        binding.toggleFav.setOnClickListener {
                            if (!statusFav) {
                                viewModel.insertFavoriteTv(detailTvShow)
                                Toast.makeText(
                                    applicationContext,
                                    "succsessfull insert",
                                    Toast.LENGTH_SHORT
                                ).show()
                                statusFav = true
                            } else {
                                viewModel.deleteFavTv(detailTvShow)
                                Toast.makeText(
                                    applicationContext,
                                    "succsessfull delete",
                                    Toast.LENGTH_SHORT
                                ).show()
                                statusFav = false
                            }
                            binding.toggleFav.isChecked = statusFav
                        }
                    }
            }
        }
    }

    private fun checkFavMovieId(id: String) {
        var count: Int
        MainScope().launch {
            withContext(Dispatchers.IO) {
                count = viewModel.cekFavMovieId(id)
            }
            if (count > 0) {
                statusFav = true
                setStatusFav(statusFav)
            } else {
                statusFav = false
                setStatusFav(statusFav)
            }

        }

    }

    private fun checkFavTvId(id: String) {
        var count: Int
        MainScope().launch {
            withContext(Dispatchers.IO) {
                count = viewModel.cekFavTvShowId(id)
            }
            if (count > 0) {
                statusFav = true
                setStatusFav(statusFav)
            } else {
                statusFav = false
                setStatusFav(statusFav)
            }
        }

    }

    private fun setStatusFav(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.toggleFav.isChecked = statusFav

        } else {
            binding.toggleFav.isChecked = statusFav
        }
    }

    private val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.back -> {
                finish()
            }
            R.id.share -> {
                startActivity(Intent.createChooser(sendIntent, null))
            }
        }
    }
}