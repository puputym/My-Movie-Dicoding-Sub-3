package com.puput.mymoviesubtiga.ui.favorite.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.databinding.ListItemFavoriteMovieBinding

class FavoriteMovieAdapter :
    PagedListAdapter<DetailMovie, FavoriteMovieAdapter.FavoriteMovieViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailMovie>() {
            override fun areItemsTheSame(oldItem: DetailMovie, newItem: DetailMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DetailMovie,
                newItem: DetailMovie
            ): Boolean {
                return oldItem == newItem
            }
        }
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w200"
    }

    interface OnItemClickCallback {
        fun onItemClicked(movie: DetailMovie)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class FavoriteMovieViewHolder(private val binding: ListItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: DetailMovie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(FavoriteMovieAdapter.IMAGE_URL + movie.posterPath)
                    .apply(RequestOptions().override(200, 250))
                    .into(imgItemPhotoFavorite)

                titleFavoriteMovie.text = movie.title
                ratingFavorite.text = movie.voteAverage.toString()

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val itemFavoriteMovieBinding =
            ListItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder((itemFavoriteMovieBinding))
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

}