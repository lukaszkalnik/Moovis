package com.lukaszkalnik.moovis.domain.usecase

import arrow.core.Either
import com.lukaszkalnik.moovis.data.model.TmdbConfiguration
import com.lukaszkalnik.moovis.data.remote.TmdbApi

typealias GetConfiguration = suspend () -> Either<Exception, TmdbConfiguration>

fun getConfigurationFactory(
    tmdbApi: TmdbApi
): GetConfiguration = tmdbApi::getConfiguration
