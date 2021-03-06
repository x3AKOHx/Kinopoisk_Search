package com.saush.kinopoisksearch.filmData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExternalId(
    @SerializedName("_id")
    val id: String?,
    val imdb: String?,
    val kpHD: String?,
    val tmdb: Int?
): Serializable