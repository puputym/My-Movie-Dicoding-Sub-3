package com.puput.mymoviesubtiga.data.local.entity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.puput.mymoviesubtiga.data.local.entity.DetailMovie
import com.puput.mymoviesubtiga.data.local.entity.DetailTVShow

@Database(entities = [DetailMovie::class, DetailTVShow::class], version = 1)

abstract class FavoriteRoomDatabase : RoomDatabase() {

    abstract fun getmovieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteRoomDatabase::class.java, "movie_database"
                    )
                        .build()
                }
            }
            return INSTANCE as FavoriteRoomDatabase
        }
    }

}