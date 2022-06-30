package com.saush.kinopoisksearch.FilmData

data class FilmInfo(
    val docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)