package com.lukaszkalnik.moovis.data

import com.lukaszkalnik.moovis.data.model.Configuration
import retrofit2.http.GET

interface TmdbApi {

    @GET("configuration")
    suspend fun getConfiguration(): Configuration
}