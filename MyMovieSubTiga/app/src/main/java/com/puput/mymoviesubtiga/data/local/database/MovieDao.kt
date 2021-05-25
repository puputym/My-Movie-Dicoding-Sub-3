package com.puput.mymoviesubtiga.data.local.entity.database

import androidx.paging.DataSource
import androidx.room.*
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: DetailMovie)

    @Delete
    fun deleteMovie(movie: DetailMovie)

    @Query("SELECT * from favorite_movie ORDER BY id ASC")
    fun getFavoriteMovie(): DataSource.Factory<Int, DetailMovie>

    @Query("SELECT count(*) FROM favorite_movie WHERE favorite_movie.id = :id")
    fun cekFavMovieById(id: String): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: DetailTVShow)

    @Delete
    fun deleteTvShow(tvShow: DetailTVShow)

    @Query("SELECT * from favorite_tvshow ORDER BY id ASC")
    fun getFavoriteTvShow(): DataSource.Factory<Int, DetailTVShow>

    @Query("SELECT count(*) FROM favorite_tvshow WHERE favorite_tvshow.id = :id")
    fun cekFavTvById(id: String): Int
}
