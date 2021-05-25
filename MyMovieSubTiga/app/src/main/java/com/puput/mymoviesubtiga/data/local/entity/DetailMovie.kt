package com.puput.mymoviesubtiga.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_movie")
@Parcelize
data class DetailMovie(


    @SerializedName("overview")
    val overview: String = "",

    @SerializedName("poster_path")
    val posterPath: String = "",

    @SerializedName("release_date")
    val releaseDate: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("runtime")
    val runtime: String,

    @PrimaryKey
    @SerializedName("id")
    val id: Int = 0,

    ) : Parcelable
