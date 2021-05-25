package com.puput.mymoviesubtiga.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow
import com.puput.mymoviesubtiga.utils.CatalogueData
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailMovieActivityViewModelTest {


    private lateinit var viewModel: DetailMovieActivityViewModel
    private val dummymovie = CatalogueData.generateDetailMovie()
    private val dummytvShow = CatalogueData.generateDetailTvShow()
    private val catalogIdMovie = dummymovie.id
    private val catalogIdTvShow = dummytvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var observerMv: Observer<DetailMovie>

    @Mock
    private lateinit var observerTv: Observer<DetailTVShow>


    @Mock
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        viewModel = DetailMovieActivityViewModel(movieRepository)
    }

    @Test
    fun getDetailMovie() {

        val movie = MutableLiveData<DetailMovie>()
        movie.value = dummymovie

        `when`(movieRepository.getAllDetailMovie(catalogIdMovie.toString()))
            .thenReturn(movie)
        val movieEntities = viewModel.getDetailMovie(catalogIdMovie.toString())?.value
        Mockito.verify<MovieRepository>(movieRepository)
            .getAllDetailMovie(catalogIdMovie.toString())

        Assert.assertNotNull(movieEntities)
        assertEquals(movie.value!!.id, movieEntities?.id)
        assertEquals(movie.value!!.posterPath, movieEntities?.posterPath)
        assertEquals(movie.value!!.releaseDate, movieEntities?.releaseDate)
        assertEquals(movie.value!!.voteAverage, movieEntities?.voteAverage)
        assertEquals(movie.value!!.overview, movieEntities?.overview)
        assertEquals(movie.value!!.title, movieEntities?.title)
        assertEquals(movie.value!!.runtime, movieEntities?.runtime)


        viewModel.getDetailMovie(catalogIdMovie.toString()).observeForever(observerMv)
        Mockito.verify(observerMv).onChanged(dummymovie)
    }

    @Test
    fun getDetailTvShow() {
        val tvShow = MutableLiveData<DetailTVShow>()
        tvShow.value = dummytvShow

        `when`(movieRepository.getAllDetailTvShow(catalogIdTvShow.toString()))
            .thenReturn(tvShow)
        val tvEntities = viewModel.getDetailTvShow(catalogIdTvShow.toString())?.value
        Mockito.verify<MovieRepository>(movieRepository)
            .getAllDetailTvShow(catalogIdTvShow.toString())

        Assert.assertNotNull(tvEntities)
        assertEquals(tvShow.value!!.id, tvEntities?.id)
        assertEquals(tvShow.value!!.posterPath, tvEntities?.posterPath)
        assertEquals(tvShow.value!!.overview, tvEntities?.overview)
        assertEquals(tvShow.value!!.firstAirDate, tvEntities?.firstAirDate)
        assertEquals(tvShow.value!!.status, tvEntities?.status)
        assertEquals(tvShow.value!!.name, tvEntities?.name)
        assertEquals(tvShow.value!!.voteAverage, tvEntities?.voteAverage)

        viewModel.getDetailTvShow(catalogIdTvShow.toString()).observeForever(observerTv)
        Mockito.verify(observerTv).onChanged(dummytvShow)
    }

    @Test
    fun cekFavMovieId() {
        `when`(movieRepository.cekFavMovieId(any())).thenReturn(1)
        val isMovie = viewModel.cekFavMovieId("1")
        assertNotNull(isMovie)

        verify(movieRepository).cekFavMovieId(any())

    }


    @ExperimentalCoroutinesApi
    @Test
    fun insertFavMovie() = runBlockingTest {

        viewModel.insertFavMovie(dummymovie)
        verify(movieRepository).insertFavMovie(any())
    }


    @Test
    fun deleteFavMovie() = runBlockingTest {

        viewModel.deleteFavMovie(dummymovie)
        verify(movieRepository).deleteFavMovie(any())
    }

    @Test
    fun cekFavTvShowId() {
        `when`(movieRepository.cekFavTvShowId(any())).thenReturn(1)
        val tv = viewModel.cekFavTvShowId("1")
        assertNotNull(tv)
        verify(movieRepository).cekFavTvShowId(any())
    }

    @Test
    fun insertFavTv() = runBlockingTest {
        viewModel.insertFavoriteTv(dummytvShow)
        verify(movieRepository).insertFavTv(any())
    }


    @Test
    fun deleteFavTv() = runBlockingTest {
        viewModel.deleteFavTv(dummytvShow)
        verify(movieRepository).deleteFavTvShow(any())
    }
}