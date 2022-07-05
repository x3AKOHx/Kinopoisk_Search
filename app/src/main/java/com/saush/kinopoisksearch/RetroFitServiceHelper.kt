package com.saush.kinopoisksearch

import com.saush.kinopoisksearch.fullFilmInfo.FullFilmInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface RetroFitServiceHelper {
    @GET
    suspend fun getInfo(
        @Url url: String,
        @Header("X-API-KEY") apiKey: String,
        @Header("Content-Type") type: String): Response<FullFilmInfo>

    companion object {
        var BASE_URL = "https://kinopoiskapiunofficial.tech/"
        var retrofitServiceHelper: RetroFitServiceHelper? = null

        fun create(): RetroFitServiceHelper {
            if (retrofitServiceHelper == null) {
                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                retrofitServiceHelper = retrofit.create(RetroFitServiceHelper::class.java)
            }
            return retrofitServiceHelper!!
        }
    }
}