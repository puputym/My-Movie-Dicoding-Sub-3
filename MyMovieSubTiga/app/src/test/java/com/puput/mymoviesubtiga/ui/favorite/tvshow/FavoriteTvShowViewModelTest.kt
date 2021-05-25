package com.puput.mymoviesubtiga.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvShowViewModelTest {

    private lateinit var viewModel: FavoriteTvShowViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observerMovie: Observer<PagedList<DetailTVShow>>

    @Mock
    private lateinit var moviePagedList: PagedList<DetailTVShow>

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(movieRepository)
    }

    @Test
    fun getFavoriteMovie() {
        val favoriteTvShow = moviePagedList


        `when`(favoriteTvShow.size).thenReturn(10)
        val movie = MutableLiveData<PagedList<DetailTVShow>>()
        movie.value = favoriteTvShow

        `when`(movieRepository.getFavoriteTvShow()).thenReturn(movie)
        val movieEntity = viewModel.getFavoriteTvShow().value
        verify<MovieRepository>(movieRepository).getFavoriteTvShow()

        assertNotNull(movieEntity)
        assertEquals(10, movieEntity?.size)

        viewModel.getFavoriteTvShow().observeForever(observerMovie)
        verify(observerMovie).onChanged(favoriteTvShow)

    }
}