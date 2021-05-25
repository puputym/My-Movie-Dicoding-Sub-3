package com.puput.mymoviesubtiga.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie


class FavoriteMovieViewModel(private val movieRepository: MovieRepository) :
    ViewModel() {

    fun getFavoriteMovie(): LiveData<PagedList<DetailMovie>> = movieRepository.getFavoriteMovie()
}