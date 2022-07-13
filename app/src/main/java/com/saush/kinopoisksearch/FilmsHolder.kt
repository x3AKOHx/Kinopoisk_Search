package com.saush.kinopoisksearch

import android.content.Context
import androidx.room.Room
import com.saush.kinopoisksearch.database.FilmDatabase
import com.saush.kinopoisksearch.filmData.Doc
import com.saush.kinopoisksearch.filmData.Poster
import com.saush.kinopoisksearch.filmData.Rating
import com.saush.kinopoisksearch.fullFilmInfo.Country
import com.saush.kinopoisksearch.fullFilmInfo.FullFilmInfo
import com.saush.kinopoisksearch.fullFilmInfo.Genre

object FilmsHolder {
    var films = mutableListOf<Doc>()
    var savedFilms = mutableListOf<Doc>()
    var savedFilmsInfo = mutableMapOf<Int, FullFilmInfo>()

    fun saveFilm(context: Context, film: Doc, filmInfo: FullFilmInfo, filmToSave: SavedFilm) {
        val db = Room.databaseBuilder(
            context,
            FilmDatabase::class.java, "filmsDB"
        ).build()

        val filmDao = db.filmDao()
        filmDao.insertAll(filmToSave)
        savedFilms.add(film)
        savedFilmsInfo[filmToSave.id!!] = filmInfo
    }

    fun loadFilms(context: Context) {
        savedFilms.clear()
        savedFilmsInfo.clear()

        val db = Room.databaseBuilder(
            context,
            FilmDatabase::class.java, "filmsDB"
        ).build()

        val filmDao = db.filmDao()
        val savedFilmsDB = filmDao.getAll()
        for (x in savedFilmsDB) {
            val doc = Doc(null, null, null, null, null, x.id,
            null, x.filmLength, x.filmName, null, Poster(null, x.poster, x.poster),
                 Rating(null, null, null, x.imdbRating, x.kpRating, null, null),
                null, null, null, x.filmYear)
            val fullFilmInfo = FullFilmInfo(null, listOf(Country(x.countries!!)), null,
                x.description, null, null, x.filmLength, listOf(Genre(x.genres!!)),
                null, null, null, null, x.id, null, null,
                null, null, x.filmName, x.poster, x.poster, null,
                null, null, null, null,
                null, null, null,
                x.imdbRating, null, x.kpRating, null, null,
                null, null, null, null, x.description,
                null, null, null, null, x.link, x.filmYear)

            savedFilms.add(doc)
            savedFilmsInfo[x.id!!] = fullFilmInfo
        }
    }

    fun removeFilm(context: Context, film: Doc) {
        val db = Room.databaseBuilder(
            context,
            FilmDatabase::class.java, "filmsDB"
        ).build()
        val filmDao = db.filmDao()

        val savedFilm = filmDao.getFilmById(film.id!!)
        filmDao.delete(savedFilm)

        savedFilms.remove(film)
        savedFilmsInfo.remove(film.id)
    }
}