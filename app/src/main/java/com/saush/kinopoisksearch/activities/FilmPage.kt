package com.saush.kinopoisksearch.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.saush.kinopoisksearch.FilmsHolder
import com.saush.kinopoisksearch.R
import com.saush.kinopoisksearch.SavedFilm
import com.saush.kinopoisksearch.filmData.Doc
import com.saush.kinopoisksearch.fullFilmInfo.FullFilmInfo
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmPage : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_page)

        val filmInfo = intent.getSerializableExtra("filmInfo") as FullFilmInfo
        val film = intent.getSerializableExtra("film") as Doc

        var isSaved = FilmsHolder.savedFilmsInfo.containsKey(film.id)

        val saveButton: ImageButton = findViewById(R.id.main_favourites)
        if (isSaved) saveButton.setImageResource(R.drawable.filled_star)
        else saveButton.setImageResource(R.drawable.empty_star)

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
        builder.build().load(film.poster!!.previewUrl)
            .placeholder(this.resources.getDrawable(R.drawable.ic_launcher_foreground, this.theme))
            .into(poster)

        var allGenres = ""
        var allCountries = ""

        for (i in 0 until filmInfo.genres!!.size - 1) {
            allGenres += "${filmInfo.genres[i].genre}, "
        }
        allGenres += "${filmInfo.genres[filmInfo.genres.size - 1].genre}."

        for (i in 0 until filmInfo.countries!!.size - 1) {
            allCountries += "${filmInfo.countries[i].country}, "
        }
        allCountries += "${filmInfo.countries[filmInfo.countries.size - 1].country}."
        val des = if ((filmInfo.shortDescription != null) && (filmInfo.shortDescription.length > 5))
            filmInfo.shortDescription
        else filmInfo.description

        filmName.text = filmInfo.nameRu
        ratingKp.text = filmInfo.ratingKinopoisk.toString()
        ratingIMDb.text = filmInfo.ratingImdb.toString()
        year.text = filmInfo.year.toString()
        length.text = filmInfo.filmLength.toString()
        genres.text = allGenres
        countries.text = allCountries
        description.text = des

        val spannableString = SpannableString(filmInfo.webUrl)
        spannableString.setSpan(URLSpan(filmInfo.webUrl), 0, filmInfo.webUrl!!.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        link.text = spannableString
        link.movementMethod = LinkMovementMethod.getInstance()

        saveButton.setOnClickListener {
            if (isSaved) {
                coroutineScope.launch(Dispatchers.IO) {
                    FilmsHolder.removeFilm(applicationContext, film)
                    handler.post {
                        saveButton.setImageResource(R.drawable.empty_star)
                        isSaved = false
                    }
                }
            } else {
                coroutineScope.launch(Dispatchers.IO) {
                    val filmToSave = SavedFilm(
                        film.id,
                        film.name,
                        film.year,
                        film.rating!!.kp,
                        film.rating.imdb,
                        film.movieLength,
                        film.poster.previewUrl,
                        allGenres,
                        allCountries,
                        des,
                        filmInfo.webUrl
                    )
                    FilmsHolder.saveFilm(applicationContext, film, filmInfo, filmToSave)
                    handler.post {
                        saveButton.setImageResource(R.drawable.filled_star)
                        isSaved = true
                    }
                }
            }
        }
    }
}