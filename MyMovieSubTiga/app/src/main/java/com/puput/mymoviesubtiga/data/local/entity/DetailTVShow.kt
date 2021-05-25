package com.puput.mymoviesubtiga.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_tvshow")
@Parcelize
data class DetailTVShow(

        @SerializedName("first_air_date")
        val firstAirDate: String = "",

        @PrimaryKey
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("overview")
        val overview: String = "",
        @field:SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("poster_path")
        val posterPath: String = "",

        @field:SerializedName("status")
        val status: String

) : Parcelable
