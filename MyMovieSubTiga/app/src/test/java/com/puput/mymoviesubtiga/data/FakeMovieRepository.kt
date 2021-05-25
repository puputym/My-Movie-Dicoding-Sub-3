package com.puput.mymoviesubtiga.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.puput.mymoviesubtiga.data.local.LocalDataSource
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow
import com.puput.mymoviesubtiga.data.local.entity.ListMovieResponse
import com.puput.mymoviesubtiga.data.local.entity.ListTvShowResponse
import com.puput.mymoviesubtiga.data.remote.RemoteDataSource
import com.puput.mymoviesubtiga.utils.Executors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FakeMovieRepository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val executors: Executors
) {

    fun getAllMovie(): LiveData<List<ListMovieResponse>> {
        val movieData = MutableLiveData<List<ListMovieResponse>>()

        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getMovie(object : RemoteDataSource.movieCallback {
                override fun onAllMovieReceived(movieResponse: List<ListMovieResponse>) {
                    movieData.postValue(movieResponse)
                }

            })
        }
        return movieData

    }

    fun getAllTVShow(): LiveData<List<ListTvShowResponse>> {
        val tvData = MutableLiveData<List<ListTvShowResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getTvShow(object : RemoteDataSource.tvShowCallback {
                override fun onAllTvShowReceived(tvShowResponse: List<ListTvShowResponse>) {
                    tvData.postValue(tvShowResponse)
                }
            })
        }
        return tvData
    }

    fun getAllDetailMovie(Id: String): LiveData<DetailMovie> {
        val movieDetailData = MutableLiveData<DetailMovie>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getDetailMovie(Id, object : RemoteDataSource.detailMovieCallback {
                override fun onDetailMovieReceive(detailMovie: DetailMovie) {
                    movieDetailData.postValue(detailMovie)
                }
            })
        }
        return movieDetailData
    }

    fun getAllDetailTvShow(Id: String): LiveData<DetailTVShow> {
        val tvShowDetailData = MutableLiveData<DetailTVShow>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getDetailTvShow(Id, object : RemoteDataSource.detailTvShowCallback {
                override fun onDetailTvShowReceived(detailTvShow: DetailTVShow) {
                    tvShowDetailData.postValue(detailTvShow)
                }
            })
        }
        return tvShowDetailData
    }


    fun getFavoriteMovie(): LiveData<PagedList<DetailMovie>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavMovie(), config).build()
    }

    fun insertFavMovie(movie: DetailMovie) = executors.diskIO().execute { localDataSource.insertMovie(movie) }


    fun deleteFavMovie(movie: DetailMovie) = executors.diskIO().execute { localDataSource.getDeleteMovie(movie) }


    fun cekFavMovieId(id: String): Int {

        return localDataSource.cekFavMovieId(id)


    }


    fun getFavoriteTvShow(): LiveData<PagedList<DetailTVShow>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavTvShow(), config).build()
    }

    fun insertFavTv(tvShow: DetailTVShow) = executors.diskIO().execute { localDataSource.insertTvShow(tvShow) }

    fun deleteFavTvShow(tvShow: DetailTVShow) = executors.diskIO().execute { localDataSource.getDeleteTvShow(tvShow) }

    fun cekFavTvShowId(id: String): Int {

        return localDataSource.cekFavTvShowId(id)

    }
}