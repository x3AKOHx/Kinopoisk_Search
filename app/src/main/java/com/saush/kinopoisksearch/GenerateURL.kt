package com.saush.kinopoisksearch

const val TOKEN = "BNB8Z5B-90FMZGY-K9XWFRQ-R3WM6YE"

object GenerateURL {
    val genres = arrayOf("Боевик", "Драма", "Мелодрама", "Комедия", "Триллер", "Ужасы", "Фантастика",
        "Фэнтези", "Детектив", "Вестерн", "Документальный", "Мюзикл", "Приключения")
    val countries = arrayOf("Россия", "США", "Англия", "Индия", "Франция", "Германия", "Испания", "СССР",
        "Япония")

    val checkedGenres = mutableListOf<Boolean>()
    val checkedCountries = mutableListOf<Boolean>()

    val selectedGenres = ArrayList<String>()
    val selectedCountries = ArrayList<String>()

    var yearFrom = 1930
    var yearTo = 2022
    var ratingFrom = 0.0

    var moviesOnly = false
    var cartoonsOnly = false

    fun getURL(): String {
        var genreReq = ""
        var countryReq = ""

        for (i in selectedGenres) {
            genreReq += "&field=genres.name&search=${i.lowercase()}"
        }

        for (i in selectedCountries) {
            countryReq += "&field=countries.name&search=$i"
        }

        val type = if (moviesOnly) "&field=typeNumber&search=1"
        else if (cartoonsOnly) "&field=typeNumber&search=3"
        else "&field=typeNumber&search=1&field=typeNumber&search=3"

        return "movie?field=rating.kp&search=$ratingFrom-10&field=year&search=$yearFrom-$yearTo" +
                "$genreReq$countryReq$type&limit=999&token=$TOKEN"
    }
}