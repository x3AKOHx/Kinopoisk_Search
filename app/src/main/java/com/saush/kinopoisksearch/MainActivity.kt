package com.saush.kinopoisksearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private lateinit var filmGenre: TextView
    private lateinit var country: TextView

    private val genres = arrayOf("Боевик", "Драма", "Комедия", "Триллер")
    private val countries = arrayOf("Россия", "США", "Англия", "Индия")

    private val checkedGenres = mutableListOf<Boolean>()
    private val checkedCountries = mutableListOf<Boolean>()

    private val selectedGenres = ArrayList<String>()
    private val selectedCountries = ArrayList<String>()

    private var yearFrom = 1930
    private var yearTo = 2022
    private var ratingFrom = 0.0

    private var moviesOnly = false
    private var cartoonsOnly = false

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

        //set slider for film year range
        val filmYear: TextView = findViewById(R.id.year_pick)
        val yearSlider: RangeSlider = findViewById(R.id.year_range)

        yearSlider.addOnChangeListener { slider, _, _ ->
            yearFrom = slider.values[0].toInt()
            yearTo = slider.values[1].toInt()
            val range = if (yearFrom == yearTo) yearFrom.toString()
                        else if (yearFrom == 1930 && yearTo == 2022) "Любой"
                        else if (yearTo == 2022) "с $yearFrom"
                        else "с $yearFrom по $yearTo"
            filmYear.text = range
        }

        //set slider for rating pick
        val rating: TextView = findViewById(R.id.rating_pick)
        val ratingSlider: Slider = findViewById(R.id.rating_slider)

        ratingSlider.addOnChangeListener { _, value, _ ->
            ratingFrom = String.format("%.1f", value).toDouble()
            rating.text = if (value.toInt() == 0) "Любой" else "от ${if 
                    (ratingFrom.toString().substringAfter('.').toInt() == 0)
                ratingFrom.toInt() else ratingFrom}"
        }

        //set listeners for switches
        val moviesOnlySwitch: SwitchCompat = findViewById(R.id.movies_only_switch)
        val cartoonsOnlySwitch: SwitchCompat = findViewById(R.id.cartoons_only_switch)

        moviesOnlySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cartoonsOnlySwitch.isEnabled = false
                moviesOnly = true
            } else {
                cartoonsOnlySwitch.isEnabled = true
                moviesOnly = false
            }
        }
        cartoonsOnlySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                moviesOnlySwitch.isEnabled = false
                cartoonsOnly = true
            } else {
                moviesOnlySwitch.isEnabled = true
                cartoonsOnly = false
            }
        }

    }
}