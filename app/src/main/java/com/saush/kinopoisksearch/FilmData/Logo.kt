package com.saush.kinopoisksearch.FilmData


import com.google.gson.annotations.SerializedName

data class Logo(
    @SerializedName("_id")
    val id: String,
    val url: String
)