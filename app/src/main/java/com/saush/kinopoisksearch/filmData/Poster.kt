package com.saush.kinopoisksearch.filmData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Poster(
    @SerializedName("_id")
    val id: String?,
    val previewUrl: String?,
    val url: String?
): Serializable