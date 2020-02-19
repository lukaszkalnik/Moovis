package com.lukaszkalnik.moovis.domain.usecase

import com.lukaszkalnik.moovis.data.model.TmdbConfiguration
import com.lukaszkalnik.moovis.data.remote.TmdbApi

typealias GetConfiguration = suspend () -> TmdbConfiguration

fun getConfigurationFactory(
    tmdbApi: TmdbApi
): GetConfiguration = tmdbApi::getConfiguration