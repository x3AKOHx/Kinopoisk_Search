package com.saush.kinopoisksearch.FilmData


import com.google.gson.annotations.SerializedName

data class Votes(
    val await: Int,
    val filmCritics: Int,
    @SerializedName("_id")
    val id: String,
    val imdb: Int,
    val kp: Int,
    val russianFilmCritics: Int
)