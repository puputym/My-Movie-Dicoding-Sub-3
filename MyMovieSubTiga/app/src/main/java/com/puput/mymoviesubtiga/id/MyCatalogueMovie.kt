package com.puput.mymoviesubtiga.id

import android.app.Application
import androidx.room.Room

import com.puput.mymoviesubtiga.data.MovieRepository
import com.puput.mymoviesubtiga.data.api.InstanceAPI
import com.puput.mymoviesubtiga.data.local.LocalDataSource
import com.puput.mymoviesubtiga.data.local.entity.database.FavoriteRoomDatabase
import com.puput.mymoviesubtiga.data.local.entity.database.MovieDao
import com.puput.mymoviesubtiga.data.remote.RemoteDataSource
import com.puput.mymoviesubtiga.ui.detail.DetailMovieActivityViewModel
import com.puput.mymoviesubtiga.ui.favorite.movie.FavoriteMovieViewModel
import com.puput.mymoviesubtiga.ui.favorite.tvshow.FavoriteTvShowViewModel
import com.puput.mymoviesubtiga.ui.movie.MovieViewModel
import com.puput.mymoviesubtiga.ui.tvshow.TvShowViewModel
import com.puput.mymoviesubtiga.utils.Executors
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyCatalogueMovie : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyCatalogueMovie)
            modules(AppModule)
        }
    }

}

val AppModule = module {
    single {
        InstanceAPI.retrofit
    }
    single {
        RemoteDataSource(get())
    }

    single {
        MovieRepository(get(), get(), get())
    }
    single {
        LocalDataSource(get())
    }
    viewModel {
        MovieViewModel(get())

    }
    viewModel {
        TvShowViewModel(get())

    }
    viewModel {
        DetailMovieActivityViewModel(get())
    }

    viewModel {
        FavoriteTvShowViewModel(get())
    }
    viewModel {
        FavoriteMovieViewModel(get())
    }

    fun provideDatabase(application: Application): FavoriteRoomDatabase {
        return Room.databaseBuilder(application, FavoriteRoomDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: FavoriteRoomDatabase): MovieDao {
        return database.getmovieDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
    single { Executors() }
}

