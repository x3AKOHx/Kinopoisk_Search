package com.saush.kinopoisksearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FilmsList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films_list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val filmItemAdapter = FilmItemAdapter(this, FilmsHolder.films)

        recyclerView.adapter = filmItemAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext, layoutManager.orientation
            )
        )
    }
}