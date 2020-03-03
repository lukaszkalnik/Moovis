package com.lukaszkalnik.moovis.domain

import com.lukaszkalnik.moovis.data.remote.TmdbApi
import com.lukaszkalnik.moovis.domain.usecase.GetConfiguration
import com.lukaszkalnik.moovis.domain.usecase.GetPopularMovies
import com.lukaszkalnik.moovis.domain.usecase.getConfigurationFactory
import com.lukaszkalnik.moovis.domain.usecase.getPopularMoviesFactory
import com.lukaszkalnik.moovis.runtimeconfiguration.data.AppConfig

/**
 * Provides use cases to be injected into view models.
 */
object UseCaseInjector {

    val getConfiguration: GetConfiguration = getConfigurationFactory(TmdbApi.instance)

    val getPopularMovies: GetPopularMovies = getPopularMoviesFactory(
        TmdbApi.instance,
        AppConfig.instance.language,
        AppConfig.instance.region
    )
}
