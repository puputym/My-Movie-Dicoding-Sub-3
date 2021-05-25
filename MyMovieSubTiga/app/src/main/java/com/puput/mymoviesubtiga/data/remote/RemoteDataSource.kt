package com.puput.mymoviesubtiga.data.remote


import com.puput.mymoviesubtiga.data.api.ServiceAPI
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow
import com.puput.mymoviesubtiga.data.local.entity.ListMovieResponse
import com.puput.mymoviesubtiga.data.local.entity.ListTvShowResponse
import com.puput.mymoviesubtiga.utils.EspressoIdlingResource
import retrofit2.await


class RemoteDataSource(private val serviceAPI: ServiceAPI) {


    suspend fun getMovie(callback: movieCallback) {
        EspressoIdlingResource.increment()
        serviceAPI.getMovie().await().results.let {
            callback.onAllMovieReceived(it)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getTvShow(callback: tvShowCallback) {
        EspressoIdlingResource.increment()
        serviceAPI.getTvShow().await().results.let {

            callback.onAllTvShowReceived(it)
            EspressoIdlingResource.decrement()

        }
    }

    suspend fun getDetailMovie(id: String, callback: detailMovieCallback) {
        EspressoIdlingResource.increment()
        serviceAPI.getDetailMovie(id).await().let {
            callback.onDetailMovieReceive(it)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getDetailTvShow(id: String, callback: detailTvShowCallback) {

        EspressoIdlingResource.increment()
        serviceAPI.getDetailTvShow(id).await().let {
            callback.onDetailTvShowReceived(it)
            EspressoIdlingResource.decrement()
        }
    }

    interface detailMovieCallback {
        fun onDetailMovieReceive(detailMovie: DetailMovie)
    }

    interface detailTvShowCallback {
        fun onDetailTvShowReceived(detailTvShow: DetailTVShow)
    }

    interface movieCallback {
        fun onAllMovieReceived(movieResponse: List<ListMovieResponse>)
    }

    interface tvShowCallback {
        fun onAllTvShowReceived(tvShowResponse: List<ListTvShowResponse>)
    }
}