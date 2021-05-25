package com.puput.mymoviesubtiga.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.local.entity.ListMovieResponse
import com.puput.mymoviesubtiga.utils.CatalogueData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {


    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<ListMovieResponse>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getMovie() {

        val dummyMovie = CatalogueData.generateMovieData()
        val movie = MutableLiveData<List<ListMovieResponse>>()
        movie.value = dummyMovie

        Mockito.`when`(movieRepository.getAllMovie()).thenReturn(movie)
        val movieEntities = viewModel.getMovie()?.value
        verify<MovieRepository>(movieRepository).getAllMovie()

        assertNotNull(movieEntities)
        assertEquals(movie.value?.size, movieEntities?.size)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}