package com.puput.mymoviesubtiga.ui.movie


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.local.entity.ListMovieResponse

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovie(): LiveData<List<ListMovieResponse>> = movieRepository.getAllMovie()
}