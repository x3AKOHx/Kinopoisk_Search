package com.saush.kinopoisksearch.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.saush.kinopoisksearch.SavedFilm

@Dao
interface FilmDao {
    @Query("SELECT * FROM savedFilm")
    fun getAll(): List<SavedFilm>

    @Query("SELECT * FROM savedFilm WHERE id=(:id)")
    fun getFilmById(id: Int): SavedFilm

    @Insert
    fun insertAll(vararg docs: SavedFilm)

    @Delete
    fun delete(savedFilm: SavedFilm)
}