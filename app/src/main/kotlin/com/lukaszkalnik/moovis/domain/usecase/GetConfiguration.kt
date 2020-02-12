package com.lukaszkalnik.moovis.domain.usecase

import com.lukaszkalnik.moovis.data.TmdbApi
import com.lukaszkalnik.moovis.data.model.Configuration

typealias GetConfiguration = suspend () -> Configuration

fun getConfigurationFactory(
    tmdbApi: TmdbApi
): GetConfiguration = tmdbApi::getConfiguration
