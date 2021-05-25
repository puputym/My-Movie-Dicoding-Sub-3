package com.puput.mymoviesubtiga.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow

class FavoriteTvShowViewModel(private val movieRepository: MovieRepository) :
    ViewModel() {

    fun getFavoriteTvShow(): LiveData<PagedList<DetailTVShow>> = movieRepository.getFavoriteTvShow()
}