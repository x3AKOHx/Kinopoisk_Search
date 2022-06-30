package com.saush.kinopoisksearch.FilmData


import com.google.gson.annotations.SerializedName

data class ExternalId(
    @SerializedName("_id")
    val id: String,
    val imdb: String
)