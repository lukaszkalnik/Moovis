package com.lukaszkalnik.moovis.domain

import com.lukaszkalnik.moovis.data.TmdbApi
import com.lukaszkalnik.moovis.data.model.Configuration

fun getConfigurationFactory(
    tmdbApi: TmdbApi
): GetConfiguration = tmdbApi::getConfiguration

typealias GetConfiguration = suspend () -> Configuration
