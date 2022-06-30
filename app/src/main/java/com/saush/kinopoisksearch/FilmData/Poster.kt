package com.saush.kinopoisksearch.FilmData


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("_id")
    val id: String,
    val previewUrl: String,
    val url: String
)