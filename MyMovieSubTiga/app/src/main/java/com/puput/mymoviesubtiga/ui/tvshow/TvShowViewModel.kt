package com.puput.mymoviesubtiga.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.local.entity.ListTvShowResponse


class TvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getAllTvShow(): LiveData<List<ListTvShowResponse>> = movieRepository.getAllTVShow()


}