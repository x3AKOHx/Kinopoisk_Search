package com.saush.kinopoisksearch.FilmData


import com.google.gson.annotations.SerializedName

data class Rating(
    val await: Double,
    val filmCritics: Double,
    @SerializedName("_id")
    val id: String,
    val imdb: Double,
    val kp: Double,
    val russianFilmCritics: Double
)