package com.puput.mymoviesubtiga.data.local.entity

import com.google.gson.annotations.SerializedName

data class MovieResponse(

        @field:SerializedName("results")
        val results: List<ListMovieResponse> = listOf(),

        )