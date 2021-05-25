package com.puput.mymoviesubtiga.data.local

import androidx.paging.DataSource
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow
import com.puput.mymoviesubtiga.data.local.entity.database.MovieDao

class LocalDataSource(private val mMovieDao: MovieDao) {


    fun insertMovie(movie: DetailMovie) = mMovieDao.insertMovie(movie)

    fun getFavMovie(): DataSource.Factory<Int, DetailMovie> = mMovieDao.getFavoriteMovie()

    fun getDeleteMovie(movie: DetailMovie) = mMovieDao.deleteMovie(movie)

    fun cekFavMovieId(id: String): Int {
        return mMovieDao.cekFavMovieById(id)
    }


    fun insertTvShow(tvShow: DetailTVShow) = mMovieDao.insertTvShow(tvShow)

    fun getFavTvShow(): DataSource.Factory<Int, DetailTVShow> = mMovieDao.getFavoriteTvShow()

    fun getDeleteTvShow(tvShow: DetailTVShow) = mMovieDao.deleteTvShow(tvShow)

    fun cekFavTvShowId(id: String): Int {
        return mMovieDao.cekFavTvById(id)
    }

}