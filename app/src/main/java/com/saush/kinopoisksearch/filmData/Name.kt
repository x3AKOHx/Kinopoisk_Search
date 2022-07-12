package com.saush.kinopoisksearch.filmData

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Name(
    @SerializedName("_id")
    val id: String?,
    val name: String?
): Serializable