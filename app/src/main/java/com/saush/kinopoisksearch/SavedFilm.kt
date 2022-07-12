package com.saush.kinopoisksearch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedFilm(
    @PrimaryKey var id: Int = 0,
    @ColumnInfo(name = "id") var filmName: String = "",
    @ColumnInfo(name = "year") var filmYear: Int = 0,
    @ColumnInfo(name = "kpRating") var kpRating: Double = 0.0,
    @ColumnInfo(name = "imdbRating") var imdbRating: Double = 0.0,
    @ColumnInfo(name = "length") var filmLength: Int = 0,
    @ColumnInfo(name = "poster") var poster: String = "",
    @ColumnInfo(name = "genres") var genres: String = "",
    @ColumnInfo(name = "countries") var countries: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "ling") var link: String = ""
)
