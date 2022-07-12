package com.saush.kinopoisksearch

import android.content.Context
import androidx.room.Room
import com.saush.kinopoisksearch.database.FilmDatabase
import com.saush.kinopoisksearch.filmData.Doc
import com.saush.kinopoisksearch.filmData.Poster
import com.saush.kinopoisksearch.filmData.Rating
import com.saush.kinopoisksearch.fullFilmInfo.FullFilmInfo
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object FilmsHolder {
    var films = mutableListOf<Doc>()
    var savedFilms = mutableListOf<Doc>()
    var savedFilmsInfo = mutableMapOf<Int, FullFilmInfo>()

    fun saveFilm(context: Context, film: SavedFilm) {
        val db = Room.databaseBuilder(
            context,
            FilmDatabase::class.java, "filmsDB"
        ).build()

        val filmDao = db.filmDao()
        filmDao.insertAll(film)
        savedFilms.add(film)
    }

    fun loadFilms(context: Context) {
        val db = Room.databaseBuilder(
            context,
            FilmDatabase::class.java, "filmsDB"
        ).build()

        val filmDao = db.filmDao()
        val savedFilms = filmDao.getAll()
        for (x in savedFilms) {
            val doc = Doc(null, null, null, null, null, x.id,
            null, x.filmLength, x.filmName, null, Poster(null, x.poster, x.poster),
                 Rating(null, null, null, x.imdbRating, x.kpRating, null, null),
                null, null, null, x.filmYear)
            val fullFilmInfo = FullFilmInfo()
        }
    }

    fun removeFilm() {

    }

}