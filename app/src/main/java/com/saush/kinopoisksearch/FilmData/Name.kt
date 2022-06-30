package com.saush.kinopoisksearch.FilmData


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("_id")
    val id: String,
    val name: String
)