package com.saush.kinopoisksearch

import com.saush.kinopoisksearch.filmData.FilmInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitService {
    @GET
    suspend fun getMovies(@Url url: String) : Response<FilmInfo>

    companion object {
        var BASE_URL = "https://api.kinopoisk.dev/"
        var retrofitService: RetrofitService? = null

        fun create(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}