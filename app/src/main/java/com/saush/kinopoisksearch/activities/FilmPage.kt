package com.saush.kinopoisksearch.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.widget.ImageView
import android.widget.TextView
import com.saush.kinopoisksearch.R
import com.saush.kinopoisksearch.fullFilmInfo.FullFilmInfo
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class FilmPage : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_page)

        val intent = intent
        val film = intent.getSerializableExtra("film") as FullFilmInfo

        val filmName: TextView = findViewById(R.id.main_film_name)
        val poster: ImageView = findViewById(R.id.main_poster)
        val ratingKp: TextView = findViewById(R.id.main_kp_rating)
        val ratingIMDb: TextView = findViewById(R.id.main_imdb_rating)
        val year: TextView = findViewById(R.id.main_year_value)
        val length: TextView = findViewById(R.id.main_film_length)
        val genres: TextView = findViewById(R.id.main_genres)
        val countries: TextView = findViewById(R.id.main_countries)
        val description: TextView = findViewById(R.id.main_film_description)
        val link: TextView = findViewById(R.id.main_kp_link)

        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this))
        builder.build().load(film.posterUrlPreview)
            .placeholder(this.resources.getDrawable(R.drawable.ic_launcher_foreground, this.theme))
            .into(poster)

        var allGenres = ""
        var allCountries = ""

        for (i in 0 until film.genres.size - 1) {
            allGenres += "${film.genres[i].genre}, "
        }
        allGenres += "${film.genres[film.genres.size - 1].genre}."

        for (i in 0 until film.countries.size - 1) {
            allCountries += "${film.countries[i].country}, "
        }
        allCountries += "${film.countries[film.countries.size - 1].country}."

        filmName.text = film.nameRu
        ratingKp.text = film.ratingKinopoisk.toString()
        ratingIMDb.text = film.ratingImdb.toString()
        year.text = film.year.toString()
        length.text = film.filmLength.toString()
        genres.text = allGenres
        countries.text = allCountries
        description.text = if (film.shortDescription != null && film.shortDescription.length > 5)
            film.shortDescription
        else film.description

        val spannableString = SpannableString(film.webUrl)
        spannableString.setSpan(URLSpan(film.webUrl), 0, film.webUrl.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        link.text = spannableString
        link.movementMethod = LinkMovementMethod.getInstance()
    }
}