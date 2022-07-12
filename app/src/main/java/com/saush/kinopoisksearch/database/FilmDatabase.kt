package com.saush.kinopoisksearch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saush.kinopoisksearch.SavedFilm
import com.saush.kinopoisksearch.dao.FilmDao

@Database(entities = [SavedFilm::class], version = 1)
abstract class FilmDatabase: RoomDatabase() {
    abstract fun filmDao(): FilmDao
}