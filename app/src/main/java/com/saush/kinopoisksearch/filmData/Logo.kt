package com.saush.kinopoisksearch.filmData


import com.google.gson.annotations.SerializedName

data class Logo(
    @SerializedName("_id")
    val id: String?,
    val url: String?
)