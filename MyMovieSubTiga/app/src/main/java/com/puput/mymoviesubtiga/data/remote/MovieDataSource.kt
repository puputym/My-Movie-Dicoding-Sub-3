package com.puput.mymoviesubtiga.data.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow
import com.puput.mymoviesubtiga.data.local.entity.ListMovieResponse
import com.puput.mymoviesubtiga.data.local.entity.ListTvShowResponse

interface MovieDataSource {


    fun getAllMovie(): LiveData<List<ListMovieResponse>>?

    fun getAllTvShow(): LiveData<List<ListTvShowResponse>>?

    fun getDetailMovie(movieId: String): LiveData<DetailMovie>?

    fun getDetailTvShow(tvId: String): LiveData<DetailTVShow>?


    fun getFavoriteMovie(): LiveData<PagedList<DetailMovie>>
    fun insertFavoriteMovie(movie: DetailMovie)
    fun deleteFavoriteMovie(movie: DetailMovie)
    fun cekFavMovieId(id: String)

    fun getFavoriteTvShow(): LiveData<PagedList<DetailTVShow>>
    fun insertFavoriteTvShow(tvShow: DetailTVShow)
    fun deleteFavoriteTvShow(tvShow: DetailTVShow)
    fun cekFavTvShowId(id: String)


}