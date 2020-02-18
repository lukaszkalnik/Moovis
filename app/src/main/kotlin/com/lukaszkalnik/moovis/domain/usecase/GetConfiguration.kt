package com.lukaszkalnik.moovis.domain.usecase

import com.lukaszkalnik.moovis.data.model.Configuration
import com.lukaszkalnik.moovis.data.remote.TmdbApi

typealias GetConfiguration = suspend () -> Configuration

fun getConfigurationFactory(
    tmdbApi: TmdbApi
): GetConfiguration = tmdbApi::getConfiguration
