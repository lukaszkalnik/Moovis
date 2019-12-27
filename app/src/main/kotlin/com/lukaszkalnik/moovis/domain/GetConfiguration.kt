package com.lukaszkalnik.moovis.domain

import com.lukaszkalnik.moovis.data.TmdbApi
import com.lukaszkalnik.moovis.data.TmdbApiService
import com.lukaszkalnik.moovis.data.model.Configuration

fun getConfiguration(
    tmdbApiService: TmdbApiService
) = tmdbApiService::getConfiguration

typealias GetConfiguration = suspend () -> Configuration
