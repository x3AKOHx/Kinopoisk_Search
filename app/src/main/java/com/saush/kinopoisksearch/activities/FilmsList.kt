package com.saush.kinopoisksearch.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saush.kinopoisksearch.FilmItemAdapter
import com.saush.kinopoisksearch.FilmsHolder
import com.saush.kinopoisksearch.R
import com.saush.kinopoisksearch.RetroFitServiceHelper
import com.saush.kinopoisksearch.filmData.Doc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmsList : AppCompatActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films_list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val filmItemAdapter = FilmItemAdapter(this, FilmsHolder.films, FilmItemAdapter.OnClickListener {
            onClick(it)
        })

        recyclerView.adapter = filmItemAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext, layoutManager.orientation
            )
        )
    }

    private fun onClick(film: Doc) {
        val kpId = film.id
        val url = "api/v2.2/films/$kpId"
        val apiKey = "ef688970-af41-4ee5-9b1b-9a44f950fc4f"
        val type = "application/json"

        coroutineScope.launch {
            val response = RetroFitServiceHelper.create().getInfo(url, apiKey, type)
            val result = response.body()!!

            val intent = Intent(applicationContext, FilmPage::class.java)
            intent.putExtra("film", result)
            startActivity(intent)
        }
    }
}