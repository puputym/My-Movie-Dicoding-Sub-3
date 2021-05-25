package com.puput.mymoviesubtiga.ui.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow


class DetailMovieActivityViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {


    fun getDetailMovie(idMovie: String): LiveData<DetailMovie> =
        movieRepository.getAllDetailMovie(idMovie)

    fun getDetailTvShow(idTv: String): LiveData<DetailTVShow> =
        movieRepository.getAllDetailTvShow(idTv)

    fun insertFavMovie(movie: DetailMovie) = movieRepository.insertFavMovie(movie)

    fun deleteFavMovie(movie: DetailMovie) = movieRepository.deleteFavMovie(movie)

    fun cekFavMovieId(id: String) = movieRepository.cekFavMovieId(id)


    fun insertFavoriteTv(tvShow: DetailTVShow) = movieRepository.insertFavTv(tvShow)

    fun deleteFavTv(tvShow: DetailTVShow) = movieRepository.deleteFavTvShow(tvShow)

    fun cekFavTvShowId(id: String) = movieRepository.cekFavTvShowId(id)


}