package com.saush.kinopoisksearch.filmData

import java.io.Serializable

data class Doc(
    val alternativeName: String?,
    val color: String?,
    val description: String?,
    val enName: String?,
    val externalId: ExternalId?,
    val id: Int?,
    val logo: Logo?,
    val movieLength: Int?,
    val name: String?,
    val names: List<Name?>?,
    val poster: Poster?,
    val rating: Rating?,
    val shortDescription: String?,
    val type: String?,
    val votes: Votes?,
    val year: Int?
): Serializable