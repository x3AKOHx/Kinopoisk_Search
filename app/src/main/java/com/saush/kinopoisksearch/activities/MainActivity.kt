package com.saush.kinopoisksearch.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.saush.kinopoisksearch.FilmsHolder
import com.saush.kinopoisksearch.GenerateURL
import com.saush.kinopoisksearch.R
import com.saush.kinopoisksearch.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var filmGenre: TextView
    private lateinit var country: TextView

    private lateinit var favouritesButton: ImageButton
    private lateinit var searchButton: Button
    private lateinit var randomSearchButton: Button

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set listeners for genre and country fields
        filmGenre = findViewById(R.id.film_genre)
        country = findViewById(R.id.country)

        filmGenre.setOnClickListener {
            if (GenerateURL.checkedGenres.isEmpty()){
                repeat(GenerateURL.genres.count()) { GenerateURL.checkedGenres.add(false)}
            }

            AlertDialog.Builder(this)
                .setTitle("Выберите жанр")
                .setCancelable(false)
                .setMultiChoiceItems(GenerateURL.genres,
                    GenerateURL.checkedGenres.toBooleanArray()) {_, i, isChecked ->
                    if (isChecked)
                        GenerateURL.selectedGenres.add(GenerateURL.genres[i])
                    else
                        GenerateURL.selectedGenres.remove(GenerateURL.genres[i])

                    GenerateURL.checkedGenres[i] = isChecked
                }
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    if (GenerateURL.selectedGenres.size > 0)
                    filmGenre.text = GenerateURL.selectedGenres.joinToString(", ")
                    else filmGenre.text = ""
                    if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
                }
                .setNegativeButton("Cancel") {i, _, ->
                    if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
                    i.dismiss()
                }
                .setNeutralButton("Clear All") { _, _ ->
                    if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
                    GenerateURL.selectedGenres.clear()
                    GenerateURL.checkedGenres.clear()
                    filmGenre.text = ""
                }
                .show()
        }
        country.setOnClickListener {
            if (GenerateURL.checkedCountries.isEmpty()){
                repeat(GenerateURL.countries.count()) { GenerateURL.checkedCountries.add(false)}
            }

            AlertDialog.Builder(this)
                .setTitle("Выберите страну")
                .setCancelable(false)
                .setMultiChoiceItems(GenerateURL.countries, GenerateURL.checkedCountries.toBooleanArray())
                {_, i, isChecked ->
                    if (isChecked)
                        GenerateURL.selectedCountries.add(GenerateURL.countries[i])
                    else
                        GenerateURL.selectedCountries.remove(GenerateURL.countries[i])

                    GenerateURL.checkedCountries[i] = isChecked
                }
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    if (GenerateURL.selectedCountries.size > 0)
                        country.text = GenerateURL.selectedCountries.joinToString(", ")
                    else country.text = ""
                    if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
                }
                .setNegativeButton("Cancel") {i, _, ->
                    if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
                    i.dismiss()
                }
                .setNeutralButton("Clear All") { _, _ ->
                    if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
                    GenerateURL.selectedCountries.clear()
                    GenerateURL.checkedCountries.clear()
                    country.text = ""
                }
                .show()
        }

        //set slider for film year range
        val filmYear: TextView = findViewById(R.id.year_pick)
        val yearSlider: RangeSlider = findViewById(R.id.year_range)

        yearSlider.addOnChangeListener { slider, _, _ ->
            if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
            GenerateURL.yearFrom = slider.values[0].toInt()
            GenerateURL.yearTo = slider.values[1].toInt()
            val range = if (GenerateURL.yearFrom == GenerateURL.yearTo) GenerateURL.yearFrom.toString()
                        else if (GenerateURL.yearFrom == 1930 && GenerateURL.yearTo == 2022) "Любой"
                        else if (GenerateURL.yearTo == 2022) "с ${GenerateURL.yearFrom}"
                        else "с ${GenerateURL.yearFrom} по ${GenerateURL.yearTo}"
            filmYear.text = range
        }

        //set slider for rating pick
        val rating: TextView = findViewById(R.id.rating_pick)
        val ratingSlider: Slider = findViewById(R.id.rating_slider)

        ratingSlider.addOnChangeListener { _, value, _ ->
            if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
            GenerateURL.ratingFrom = String.format("%.1f", value).toDouble()
            rating.text = if (value.toInt() == 0) "Любой" else "от ${if 
                    (GenerateURL.ratingFrom.toString().substringAfter('.').toInt() == 0)
                GenerateURL.ratingFrom.toInt() else GenerateURL.ratingFrom
            }"
        }

        //set listeners for switches
        val moviesOnlySwitch: SwitchCompat = findViewById(R.id.movies_only_switch)
        val cartoonsOnlySwitch: SwitchCompat = findViewById(R.id.cartoons_only_switch)

        moviesOnlySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cartoonsOnlySwitch.isEnabled = false
                GenerateURL.moviesOnly = true
            } else {
                cartoonsOnlySwitch.isEnabled = true
                GenerateURL.moviesOnly = false
            }
            if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
        }
        cartoonsOnlySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                moviesOnlySwitch.isEnabled = false
                GenerateURL.cartoonsOnly = true
            } else {
                moviesOnlySwitch.isEnabled = true
                GenerateURL.cartoonsOnly = false
            }
            if (FilmsHolder.films.size != 0) FilmsHolder.films.clear()
        }

        //set listeners for buttons
        favouritesButton = findViewById(R.id.favourite)
        searchButton = findViewById(R.id.search_button)
        randomSearchButton = findViewById(R.id.random_button)

        favouritesButton.setOnClickListener { view ->
            favouriteMovies(view)
        }
        searchButton.setOnClickListener { view ->
            search(view)
        }
        randomSearchButton.setOnClickListener { view ->
            getRandomMovie(view)
        }
    }

    private fun favouriteMovies(view: View) {

    }

    private fun search(view: View) {
        if (FilmsHolder.films.size == 0) {
            val progressBar: ProgressBar = findViewById(R.id.main_progress_bar)
            progressBar.visibility = View.VISIBLE
            favouritesButton.isEnabled = false
            searchButton.isEnabled = false
            randomSearchButton.isEnabled = false
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val response = RetrofitService.create().getMovies(GenerateURL.getURL())
                    val result = response.body()
                    if (result != null) {
                        FilmsHolder.films.addAll(result.docs.filter {
                            it.movieLength > 60 && it.name.isNotEmpty()
                        })
                    }
                } catch (e: Exception) {
                    handler.post {
                        Toast.makeText(applicationContext, "Отсутствует соединение с сервером",
                            Toast.LENGTH_LONG).show()
                    }
                    Log.e("Wrong request", "${e.stackTrace}")
                }
                handler.post {
                    progressBar.visibility = View.INVISIBLE
                    favouritesButton.isEnabled = true
                    searchButton.isEnabled = true
                    randomSearchButton.isEnabled = true
                }
                if (FilmsHolder.films.size == 0) {
                    handler.post {
                        Toast.makeText(
                            applicationContext,
                            "По вашему запросу ничего не нашлось",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                } else {
                    val intent = Intent(applicationContext, FilmsList::class.java)
                    startActivity(intent)
                }
            }
        } else {
            val intent = Intent(applicationContext, FilmsList::class.java)
            startActivity(intent)
        }
    }

    private fun getRandomMovie(view: View) {

    }
}