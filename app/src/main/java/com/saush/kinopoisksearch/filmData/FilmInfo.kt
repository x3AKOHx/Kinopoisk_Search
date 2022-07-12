package com.saush.kinopoisksearch.filmData

import java.io.Serializable

data class FilmInfo(
    val docs: List<Doc>,
    val limit: Int?,
    val page: Int?,
    val pages: Int?,
    val total: Int?
): Serializable