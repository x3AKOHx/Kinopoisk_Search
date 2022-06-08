package com.saush.kinopoisksearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.slider.RangeSlider

class MainActivity : AppCompatActivity() {

    private lateinit var filmGenre: TextView
    private lateinit var country: TextView

    private val genres = arrayOf("Боевик", "Драма", "Комедия", "Триллер")
    private val countries = arrayOf("Россия", "США", "Англия", "Индия")

    private val checkedGenres = mutableListOf<Boolean>()
    private val checkedCountries = mutableListOf<Boolean>()

    private val selectedGenres = ArrayList<String>()
    private val selectedCountries = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set listeners for genre and country fields
        filmGenre = findViewById(R.id.film_genre)
        country = findViewById(R.id.country)

        filmGenre.setOnClickListener {
            if (checkedGenres.isEmpty()){
                repeat(genres.count()) {checkedGenres.add(false)}
            }

            AlertDialog.Builder(this)
                .setTitle("Выберите жанр")
                .setCancelable(false)
                .setMultiChoiceItems(genres, checkedGenres.toBooleanArray()) {_, i, isChecked ->
                    if (isChecked)
                        selectedGenres.add(genres[i])
                    else
                        selectedGenres.remove(genres[i])

                    checkedGenres[i] = isChecked
                }
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    if (selectedGenres.size > 0)
                    filmGenre.text = selectedGenres.joinToString(", ")
                    else filmGenre.text = ""
                }
                .setNegativeButton("Cancel") {i, _, ->
                    i.dismiss()
                }
                .setNeutralButton("Clear All") { _, _ ->
                    selectedGenres.clear()
                    checkedGenres.clear()
                    filmGenre.text = ""
                }
                .show()
        }
        country.setOnClickListener {
            if (checkedCountries.isEmpty()){
                repeat(countries.count()) {checkedCountries.add(false)}
            }

            AlertDialog.Builder(this)
                .setTitle("Выберите страну")
                .setCancelable(false)
                .setMultiChoiceItems(countries, checkedCountries.toBooleanArray()) {_, i, isChecked ->
                    if (isChecked)
                        selectedCountries.add(countries[i])
                    else
                        selectedCountries.remove(countries[i])

                    checkedCountries[i] = isChecked
                }
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    if (selectedCountries.size > 0)
                        country.text = selectedCountries.joinToString(", ")
                    else country.text = ""
                }
                .setNegativeButton("Cancel") {i, _, ->
                    i.dismiss()
                }
                .setNeutralButton("Clear All") { _, _ ->
                    selectedCountries.clear()
                    checkedCountries.clear()
                    country.text = ""
                }
                .show()
        }

        //set sliders for film year and rating picks
        val filmYear: TextView = findViewById(R.id.year_pick)

        val discreteRangeSlider: RangeSlider = findViewById(R.id.year_range)
        discreteRangeSlider.addOnChangeListener { slider, _, _ ->
            val from = slider.values[0].toInt()
            val to = slider.values[1].toInt()
            val range = if (from == to) from.toString()
                        else if (from == 1930 && to == 2022) "Любой"
                        else if (to == 2022) "с $from"
                        else "с $from по $to"
            filmYear.text = range
        }



    }
}