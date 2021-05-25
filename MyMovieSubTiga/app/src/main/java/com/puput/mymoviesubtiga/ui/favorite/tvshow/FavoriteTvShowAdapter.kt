package com.puput.mymoviesubtiga.ui.favorite.tvshow

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow
import com.puput.mymoviesubtiga.databinding.ListItemFavoriteTvshowBinding

class FavoriteTvShowAdapter :
    PagedListAdapter<DetailTVShow, FavoriteTvShowAdapter.FavoriteTvShowViewHolder>(DIFF_CALLBACK) {


    private lateinit var onItemClickCallback: OnItemClickCallback


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailTVShow>() {
            override fun areItemsTheSame(
                oldItem: DetailTVShow,
                newItem: DetailTVShow
            ): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: DetailTVShow,
                newItem: DetailTVShow
            ): Boolean {
                return oldItem == newItem
            }
        }
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w200"
    }

    interface OnItemClickCallback {
        fun onItemClicked(tvshow: DetailTVShow)
    }

    fun setOnItemClickCallbackFavoriteTv(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class FavoriteTvShowViewHolder(private val binding: ListItemFavoriteTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvshow: DetailTVShow) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(FavoriteTvShowAdapter.IMAGE_URL + tvshow.posterPath)
                    .apply(RequestOptions().override(200, 250))
                    .into(imgItemPhotoFavorite)

                titleFavoriteMovie.text = tvshow.name
                ratingFavorite.text = tvshow.voteAverage.toString()

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(tvshow)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTvShowViewHolder {
        val itemFavoriteTvshowBinding = ListItemFavoriteTvshowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteTvShowViewHolder(itemFavoriteTvshowBinding)
    }

    override fun onBindViewHolder(holder: FavoriteTvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }
}