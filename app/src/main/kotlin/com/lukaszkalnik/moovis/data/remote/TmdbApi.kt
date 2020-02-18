package com.lukaszkalnik.moovis.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lukaszkalnik.moovis.BuildConfig
import com.lukaszkalnik.moovis.data.model.Configuration
import com.lukaszkalnik.moovis.runtimeconfiguration.RuntimeConfiguration
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET

interface TmdbApi {

    @GET("configuration")
    suspend fun getConfiguration(): Configuration
}

private const val TMDB_API_URL = "https://api.themoviedb.org/3/"

/**
 * An instance of [TmdbApi] to perform API calls.
 */
val tmdbApi: TmdbApi by lazy {

    val authenticatedClient = OkHttpClient.Builder()
        .addInterceptor(authenticationInterceptor)
        .addInterceptor(localizationInterceptor)
        .build()

    val contentType = MediaType.get("application/json")

    Retrofit.Builder()
        .baseUrl(TMDB_API_URL)
        .client(authenticatedClient)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
        .create(TmdbApi::class.java)
}

private const val QUERY_PARAM_API_KEY = "api_key"

/**
 * Interceptor to authenticate API calls.
 */
private val authenticationInterceptor = Interceptor { chain ->
    with(chain) {
        val authenticatedUrl = request().url()
            .newBuilder()
            .addQueryParameter(QUERY_PARAM_API_KEY, BuildConfig.TMDB_API_KEY)
            .build()

        val authenticatedRequest = request()
            .newBuilder()
            .url(authenticatedUrl)
            .build()

        proceed(authenticatedRequest)
    }
}

private const val QUERY_PARAM_LANGUAGE = "language"

/**
 * Interceptor to request localized API responses in current user locale.
 */
private val localizationInterceptor = Interceptor { chain ->
    with(chain) {
        val localizedUrl = request().url()
            .newBuilder()
            .addQueryParameter(QUERY_PARAM_LANGUAGE, RuntimeConfiguration.locale)
            .build()

        val localizedRequest = request()
            .newBuilder()
            .url(localizedUrl)
            .build()

        chain.proceed(localizedRequest)
    }
}
