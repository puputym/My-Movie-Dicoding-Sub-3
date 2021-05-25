package com.puput.mymoviesubtiga.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.puput.mymoviesubtiga.data.local.LocalDataSource
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow
import com.puput.mymoviesubtiga.data.remote.RemoteDataSource
import com.puput.mymoviesubtiga.utils.CatalogueData
import com.puput.mymoviesubtiga.utils.Executors
import com.puput.mymoviesubtiga.utils.LiveDataTestUtil
import com.puput.mymoviesubtiga.utils.PagedListUtil
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val localDataSource = mock(LocalDataSource::class.java)
    private val executors = mock(Executors::class.java)
    private var fakeMovieRepository = FakeMovieRepository(remote, localDataSource, executors)

    private val movieResponse = CatalogueData.generateMovieData()
    private val tvShowResponse = CatalogueData.generateTvShow()


    private val movieDetailResponse = CatalogueData.generateDetailMovie()
    private val movieId = movieDetailResponse.id

    private val tvShowDetailResponse = CatalogueData.generateDetailTvShow()
    private val tvId = tvShowDetailResponse.id

    @Test
    fun getAllMovie() {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[0] as RemoteDataSource.movieCallback)
                        .onAllMovieReceived(movieResponse)
                null
            }.`when`(remote).getMovie(any())
        }

        val movie = LiveDataTestUtil.getValue(fakeMovieRepository.getAllMovie())
        runBlocking {
            verify(remote).getMovie(any())
        }
        Assert.assertNotNull(movie)
        assertEquals(movieResponse.size, movie.size)
    }

    @Test
    fun getAllTvShow() {
        runBlocking {
            Mockito.doAnswer { invocation ->
                (invocation.arguments[0] as RemoteDataSource.tvShowCallback)
                        .onAllTvShowReceived(tvShowResponse)
                null
            }.`when`(remote).getTvShow(any())
        }
        val tvShow = LiveDataTestUtil.getValue(fakeMovieRepository.getAllTVShow())
        runBlocking {
            verify(remote).getTvShow(any())
        }
        Assert.assertNotNull(tvShow)
        assertEquals(tvShowResponse.size, tvShow.size)
    }

    @Test
    fun getAllDetailMovie() {
        runBlocking {
            Mockito.doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.detailMovieCallback)
                        .onDetailMovieReceive(movieDetailResponse)
                null
            }.`when`(remote).getDetailMovie(eq(movieId.toString()), any())
        }
        val detilMovie =
                LiveDataTestUtil.getValue(fakeMovieRepository.getAllDetailMovie(movieId.toString()))
        runBlocking {
            verify(remote).getDetailMovie(eq(movieId.toString()), any())
        }
        Assert.assertNotNull(detilMovie)
        assertEquals(movieDetailResponse.id.toString(), detilMovie.id.toString())
    }

    @Test
    fun getAllDetailTvShow() {
        runBlocking {
            Mockito.doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.detailTvShowCallback)
                        .onDetailTvShowReceived(tvShowDetailResponse)
                null
            }.`when`(remote).getDetailTvShow(eq(tvId.toString()), any())
        }
        val detailtvShow =
                LiveDataTestUtil.getValue(fakeMovieRepository.getAllDetailTvShow(tvId.toString()))

        runBlocking {
            verify(remote)
                    .getDetailTvShow(eq(tvId.toString()), any())
        }
        Assert.assertNotNull(detailtvShow)
        assertEquals(tvShowDetailResponse.id.toString(), detailtvShow.id.toString())
    }


    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory =
                mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DetailMovie>
        `when`(localDataSource.getFavMovie()).thenReturn(dataSourceFactory)
        fakeMovieRepository.getFavoriteMovie()

        val favMovieEntity = PagedListUtil.mockPagedList(movieResponse)
        verify(localDataSource).getFavMovie()

        assertNotNull(favMovieEntity)
        assertEquals(movieResponse.size, favMovieEntity.size)

    }

    @Test
    fun getFavoriteTv() {
        val dataSourceFactory =
                mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DetailTVShow>
        lenient().`when`(localDataSource.getFavTvShow()).thenReturn(dataSourceFactory)
        fakeMovieRepository.getFavoriteTvShow()

        val favtvEntity = PagedListUtil.mockPagedList(tvShowResponse)
        verify(localDataSource).getFavTvShow()

        assertNotNull(favtvEntity)
        assertEquals(tvShowResponse.size, favtvEntity.size)
    }

    @Test
    fun getFavoriteMovieId() {
        `when`(localDataSource.cekFavMovieId(any())).thenReturn(1)
        val movie = fakeMovieRepository.cekFavMovieId("1")

        assertNotNull(movie)
        verify(localDataSource).cekFavMovieId(any())

    }

    @Test
    fun getFavoriteTvId() {
        `when`(localDataSource.cekFavTvShowId(any())).thenReturn(1)
        val tv = fakeMovieRepository.cekFavTvShowId("1")

        assertNotNull(tv)
        Mockito.verify(localDataSource).cekFavTvShowId(any())
    }

    @Test
    fun insertFavoriteMovie() {
        `when`(executors.diskIO()).thenReturn(Executor
        {
            localDataSource.insertMovie(movieDetailResponse)
        })
        fakeMovieRepository.insertFavMovie(
                movieDetailResponse
        )
        verify(executors).diskIO()
        verify(localDataSource).insertMovie(movieDetailResponse)
    }

//    = runBlockingTest {
//
//        try {
//            fakeMovieRepository.insertFavMovie(movieDetailResponse)
//            assertTrue(true)
//
//        } catch (e: Exception) {
//            assertTrue(false)
//
//        }
//    }


    @Test
    fun insertFavoriteTv() {
        `when`(executors.diskIO()).thenReturn(Executor
        {
            localDataSource.insertTvShow(tvShowDetailResponse)
        })
        fakeMovieRepository.insertFavTv(
                tvShowDetailResponse
        )
        verify(executors).diskIO()
        verify(localDataSource).insertTvShow(tvShowDetailResponse)
    }

//    = runBlockingTest {
//
//            fakeMovieRepository.insertFavTv(tvShowDetailResponse)
//            verify(localDataSource).insertMovie(any())
//    }


    @Test
    fun deleteFavoriteMovie() {
        `when`(executors.diskIO()).thenReturn(Executor
        {
            localDataSource.getDeleteMovie(movieDetailResponse)
        })
        fakeMovieRepository.deleteFavMovie(
                movieDetailResponse
        )
        verify(executors).diskIO()
        verify(localDataSource).getDeleteMovie(movieDetailResponse)
    }


//    = runBlockingTest {
//
//        try {
//            fakeMovieRepository.deleteFavMovie(movieDetailResponse)
//            assertTrue(true)
//
//        } catch (e: Exception) {
//            assertTrue(false)
//
//        }
//    }


    @Test
    fun deleteFavoriteTv() {
        `when`(executors.diskIO()).thenReturn(Executor
        {
            localDataSource.getDeleteTvShow(tvShowDetailResponse)
        })
        fakeMovieRepository.deleteFavTvShow(
                tvShowDetailResponse
        )
        verify(executors).diskIO()
        verify(localDataSource).getDeleteTvShow(tvShowDetailResponse)
    }

//    = runBlockingTest {
//        try {
//            fakeMovieRepository.deleteFavTvShow(tvShowDetailResponse)
//            assertTrue(true)
//
//        } catch (e: Exception) {
//            assertTrue(false)
//
//        }
//
//    }

}
