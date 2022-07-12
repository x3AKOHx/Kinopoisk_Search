package com.saush.kinopoisksearch.filmData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(
    val await: Double?,
    val filmCritics: Double?,
    @SerializedName("_id")
    val id: String?,
    val imdb: Double?,
    val kp: Double?,
    val russianFilmCritics: Double?,
    val tmdb: Double?
): Serializable