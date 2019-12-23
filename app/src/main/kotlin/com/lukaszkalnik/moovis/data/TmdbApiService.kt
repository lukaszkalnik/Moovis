package com.lukaszkalnik.moovis.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lukaszkalnik.moovis.data.model.Configuration
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private const val TMDB_API_URL = "https://api.themoviedb.org/3/"

interface TmdbApiService {

    suspend fun getConfiguration(): Configuration

    companion object {

        val tmdbService: TmdbApiService by lazy {
            val contentType = MediaType.get("application/json")
            val tmdbApi = Retrofit.Builder()
                .baseUrl(TMDB_API_URL)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
                .create(TmdbApi::class.java)

            DefaultTmdbApiService(tmdbApi)
        }
    }
}

class DefaultTmdbApiService(
    private val tmdbApi: TmdbApi
) : TmdbApiService {

    override suspend fun getConfiguration(): Configuration = tmdbApi.getConfiguration()
}