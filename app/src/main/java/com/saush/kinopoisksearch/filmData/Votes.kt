package com.saush.kinopoisksearch.filmData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Votes(
    val await: Int?,
    val filmCritics: Int?,
    @SerializedName("_id")
    val id: String?,
    val imdb: Int?,
    val kp: Int?,
    val russianFilmCritics: Int?,
    val tmdb: Int?
): Serializable