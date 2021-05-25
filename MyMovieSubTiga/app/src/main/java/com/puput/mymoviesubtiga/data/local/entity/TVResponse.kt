package com.puput.mymoviesubtiga.data.local.entity

import com.google.gson.annotations.SerializedName

data class TVResponse(
    @SerializedName("results")
    val results: List<ListTvShowResponse>,

    )