package com.lukaszkalnik.moovis.domain

import com.lukaszkalnik.moovis.data.TmdbApiService
import com.lukaszkalnik.moovis.data.model.Configuration

fun getConfiguration(
    tmdbApiService: TmdbApiService
): GetConfiguration = tmdbApiService::getConfiguration

typealias GetConfiguration = suspend () -> Configuration
