package com.puput.mymoviesubtiga.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
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
class FavoriteMovieViewModelTest {


    private lateinit var viewModel: FavoriteMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observerMovie: Observer<PagedList<DetailMovie>>

    @Mock
    private lateinit var moviePagedList: PagedList<DetailMovie>

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(movieRepository)
    }

    @Test
    fun getFavoriteMovie() {
        val favoriteMovie = moviePagedList

        `when`(favoriteMovie.size).thenReturn(10)
        val movie = MutableLiveData<PagedList<DetailMovie>>()
        movie.value = favoriteMovie

        `when`(movieRepository.getFavoriteMovie()).thenReturn(movie)
        val movieEntity = viewModel.getFavoriteMovie().value
        verify(movieRepository).getFavoriteMovie()

        assertNotNull(movieEntity)
        assertEquals(10, movieEntity?.size)

        viewModel.getFavoriteMovie().observeForever(observerMovie)
        verify(observerMovie).onChanged(favoriteMovie)

    }
}